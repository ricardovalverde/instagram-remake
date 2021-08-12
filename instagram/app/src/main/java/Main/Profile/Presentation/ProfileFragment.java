package Main.Profile.Presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import Main.Presentation.MainView;
import butterknife.BindView;
import butterknife.OnClick;
import common.model.Post;
import common.view.AbstractFragment;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends AbstractFragment<ProfilePresenter> implements MainView.ProfileView {

    @BindView(R.id.profile_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.profile_image_icon)
    CircleImageView imageViewProfile;
    @BindView(R.id.profile_text_view_username)
    TextView txtUsername;
    @BindView(R.id.profile_text_view_following_count)
    TextView followingCount;
    @BindView(R.id.profile_text_view_followers_count)
    TextView followersCount;
    @BindView(R.id.profile_text_view_post_count)
    TextView postCount;
    @BindView(R.id.profile_navigation_tabs)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.profile_button_edit_profile)
    Button buttonProfile;

    private MainView mainView;
    private PostAdapter postAdapter;

    public static ProfileFragment newInstance(MainView mainView, ProfilePresenter profilePresenter) {
        ProfileFragment profileFragment = new ProfileFragment();

        profileFragment.setPresenter(profilePresenter);
        profileFragment.setMainView(mainView);
        profilePresenter.setView(profileFragment);

        return profileFragment;
    }


    private void setMainView(MainView mainView) {
        this.mainView = mainView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_profile_grid:
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    return true;
                case R.id.menu_profile_list:
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    return true;
            }
            return false;
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.findUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        postAdapter = new PostAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(postAdapter);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!presenter.getUser().equals(FirebaseAuth.getInstance().getUid())) {
                    mainView.disposeProfileDetail();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPhoto(String photo) {
        Glide.with(getContext()).load(photo).into(imageViewProfile);
    }

    @Override
    public void showData(String name, String following, String followers, String nPosts, boolean editProfile, boolean follow) {
        txtUsername.setText(name);
        followingCount.setText(following);
        followersCount.setText(followers);
        postCount.setText(nPosts);

        if (editProfile) {
            buttonProfile.setText(R.string.edit_profile);
            buttonProfile.setTag(null);
        } else if (follow) {
            buttonProfile.setText(R.string.unfollow);
            buttonProfile.setTag(false);

        } else {
            buttonProfile.setText(R.string.follow);
            buttonProfile.setTag(true);
        }

    }

    @OnClick(R.id.profile_button_edit_profile)
    public void onButtonProfileClick() {
        Boolean follow = (Boolean) buttonProfile.getTag();
        if (follow != null) {
            buttonProfile.setText(follow ? R.string.unfollow : R.string.follow);
            presenter.follow(follow);
            buttonProfile.setTag(!follow);
        }
    }

    @Override
    public void showPosts(List<Post> posts) {
        postAdapter.setPosts(posts);
        postAdapter.notifyDataSetChanged();
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
        return R.layout.fragment_main_profile;
    }

    private static class viewHolder extends RecyclerView.ViewHolder {
        private final ImageView imagePost;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.profile_image_grid);
        }

        public void bind(Post post) {
            Glide.with(itemView.getContext()).load(post).into(imagePost);
        }
    }

    private class PostAdapter extends RecyclerView.Adapter<viewHolder> {

        private List<Post> posts = new ArrayList<>();

        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }


        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new viewHolder(getLayoutInflater().inflate(R.layout.item_profile_grid, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {
            holder.bind(posts.get(position));

        }


        @Override
        public int getItemCount() {
            return posts.size();
        }
    }

}
