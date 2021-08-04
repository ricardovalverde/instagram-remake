package Main.Search.Presentation;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;

import java.util.ArrayList;
import java.util.List;

import Main.Presentation.MainView;
import butterknife.BindView;
import common.model.User;
import common.view.AbstractFragment;

public class SearchFragment extends AbstractFragment<SearchPresenter> implements MainView.SearchView {

    @BindView(R.id.search_recycler_view)
    RecyclerView recyclerView;

    private searchAdapter adapter;
    private MainView mainView;

    public static SearchFragment newInstance(MainView mainView, SearchPresenter presenter) {
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setPresenter(presenter);
        searchFragment.setMainView(mainView);
        presenter.setView(searchFragment);

        return searchFragment;

    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        adapter = new searchAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        androidx.appcompat.widget.SearchView searchView = null;

        if (searchItem != null) {
            searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(((AppCompatActivity) getContext()).getComponentName()));

            searchView.setOnQueryTextFocusChangeListener((view, b) -> Log.i("teste", b + ""));
            searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()) {
                        presenter.findUsers(newText);
                    }
                    return false;
                }
            });
            searchItem.expandActionView();
        }
    }

    @Override
    public void showUsers(List<User> users) {
        adapter.setUsers(users, user -> mainView.showProfile(user.getUuid()));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_search;
    }

    public interface ItemClickListener {
        void onClick(User user);
    }

    private static class searchHolder extends RecyclerView.ViewHolder {
        private final ImageView userImage;
        private final TextView username;
        private final TextView name;

        public searchHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.main_search_imageview_user);
            username = itemView.findViewById(R.id.main_search_text_view_username);
            name = itemView.findViewById(R.id.main_search_text_view_name);
        }

        public void bind(User user) {
            this.userImage.setImageURI(user.getUri());
            this.username.setText(user.getName());
            this.name.setText(user.getName());
        }
    }

    private class searchAdapter extends RecyclerView.Adapter<searchHolder> {

        private List<User> users = new ArrayList<>();
        private ItemClickListener listener;

        public void setUsers(List<User> users, ItemClickListener listener) {
            this.users = users;
            this.listener = listener;
        }

        @NonNull
        @Override
        public searchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_search_user_list, parent, false);
            return new searchHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull searchHolder holder, int position) {
            holder.bind(users.get(position));
            holder.itemView.setOnClickListener(view -> {
                listener.onClick(users.get(position));
            });
        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }


}
