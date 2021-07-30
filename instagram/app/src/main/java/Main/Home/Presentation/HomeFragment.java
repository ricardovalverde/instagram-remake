package Main.Home.Presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
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


    private class FeedAdapter extends RecyclerView.Adapter<homeViewHolder> {


        private List<Feed> feed = new ArrayList<>();

        public void setFeed(List<Feed> feed) {
            this.feed = feed;
        }

        @NonNull
        @Override
        public homeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new homeViewHolder(getLayoutInflater().inflate(R.layout.item_post_list, parent, false));
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

    private class homeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public homeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image_grid);
        }

        public void bind(Feed feed) {
            this.imageView.setImageURI(feed.getUri());

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
    protected int getLayout() {
        return R.layout.fragment_main_home;
    }
}
