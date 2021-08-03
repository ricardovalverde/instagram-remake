package Main.SearchPresentation;

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
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;

public class SearchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_search, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new adapter());


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
        SearchView searchView = null;

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(((AppCompatActivity) getContext()).getComponentName()));
            searchView.setOnQueryTextFocusChangeListener((view, b) -> Log.i("teste", b + ""));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }

    }

    private class adapter extends RecyclerView.Adapter<viewHolder> {
        private final int[] imagens = new int[]{
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart,
                R.drawable.ic_insta_heart

        };

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new viewHolder(getLayoutInflater().inflate(R.layout.item_search_user_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            holder.bind(imagens[position]);
        }

        @Override
        public int getItemCount() {
            return imagens.length;
        }
    }

    private class viewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.main_search_imageview_user);
        }

        public void bind(int image) {
            this.imageView.setImageResource(image);
        }
    }
}
