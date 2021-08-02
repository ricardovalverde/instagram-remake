package Main.Home.Presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;

import java.util.ArrayList;
import java.util.List;

import Main.Presentation.MainView;
import butterknife.BindView;
import common.model.Feed;
import common.view.AbstractFragment;

public class HomeFragment extends AbstractFragment<HomePresenter> implements MainView.HomeView {

    private MainView mainView;
    private FeedAdapter feedAdapter;

    @BindView(R.id.home_recycler_view)
    RecyclerView recyclerView;


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
    public void onResume() {
        super.onResume();
        presenter.findFeed();
    }

    @Override
    public void showFeed(List<Feed> feed) {
        feedAdapter.setFeed(feed);
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);

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

    public static class homeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView images;

        public homeViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.post_image_grid);
        }

        public void bind(Feed feed) {
            this.images.setImageURI(feed.getUri());

        }
    }

    @Override
    public void showProgressBar() {
        mainView.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        mainView.hideProgressBar();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_main_home;
    }
}
