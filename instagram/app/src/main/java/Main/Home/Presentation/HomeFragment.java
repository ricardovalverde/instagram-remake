package Main.Home.Presentation;

import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import Main.Presentation.MainView;
import butterknife.BindView;
import common.component.CustomDialog;
import common.model.Feed;
import common.model.User;
import common.view.AbstractFragment;

public class HomeFragment extends AbstractFragment<HomePresenter> implements MainView.HomeView {

    @BindView(R.id.home_recycler_view)
    RecyclerView recyclerView;

    private MainView mainView;
    private FeedAdapter feedAdapter;

    public static HomeFragment newInstance(MainView mainView, HomePresenter homePresenter) {
        HomeFragment homeFragment = new HomeFragment();

        homeFragment.setPresenter(homePresenter);
        homeFragment.setMainView(mainView);
        homePresenter.setView(homeFragment);

        return homeFragment;
    }

    private void setMainView(MainView mainview) {
        this.mainView = mainview;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        feedAdapter = new FeedAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(feedAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void showFeed(List<Feed> feed) {
        feedAdapter.setFeed(feed);
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                CustomDialog customDialog = new CustomDialog.Builder(getContext())
                        .setTitle(R.string.logout)
                        .addButton((view) -> {
                            switch (view.getId()) {
                                case R.string.logout_action:
                                    FirebaseAuth.getInstance().signOut();
                                    mainView.logout();
                                    break;
                                case R.string.cancel:
                                    break;
                            }
                        }, R.string.logout_action, R.string.cancel)
                        .build();
                customDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.findFeed();
    }

    @Override
    public void showProgressBar() {
        mainView.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        mainView.hideProgressBar();
    }

    public static class homeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imagesPost;
        private final ImageView userImage;
        private final TextView username;
        private final TextView caption;


        public homeViewHolder(@NonNull View itemView) {
            super(itemView);
            imagesPost = itemView.findViewById(R.id.post_image_grid);
            userImage = itemView.findViewById(R.id.home_image_user);
            username = itemView.findViewById(R.id.home_username);
            caption = itemView.findViewById(R.id.home_image_caption);
        }

        public void bind(Feed feed) {
            Glide.with(itemView.getContext()).load(feed.getPhotoUrl()).into(this.imagesPost);
            this.caption.setText(feed.getCaption());

            User user = feed.getPublisher();
            if (user != null) {
                Glide.with(itemView.getContext()).load(user.getUrlPhoto()).into(this.userImage);
                this.username.setText(user.getName());
            }
        }
    }

    public class FeedAdapter extends RecyclerView.Adapter<homeViewHolder> {


        private List<Feed> feed = new ArrayList<>();

        public void setFeed(List<Feed> feed) {
            this.feed = feed;
        }


        @NonNull
        @Override
        public homeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_post_list, parent, false);
            return new homeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull homeViewHolder holder, int position) {
            holder.bind(feed.get(position));
        }

        @Override
        public int getItemCount() {
            return feed.size();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_main_home;
    }
}
