package Main.Profile.Presentation;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Main.Presentation.MainView;
import butterknife.BindView;
import common.model.Post;
import common.view.AbstractFragment;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends AbstractFragment<ProfilePresenter> implements MainView.ProfileView {

    @BindView(R.id.profile_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.profile_image_icon)
    CircleImageView profileImage;
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
    public void showPhoto(Uri photo) {
        if (getContext() != null && getContext().getContentResolver() != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photo);

                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e("ShowPhoto", e.getMessage(), e);
            }
        }
    }

    @Override
    public void showData(String name, String following, String followers, String nPosts) {
        txtUsername.setText(name);
        followingCount.setText(following);
        followersCount.setText(followers);
        postCount.setText(nPosts);
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
        private final ImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image_grid);
        }

        public void bind(Post post) {
            this.imageView.setImageURI(post.getUri());
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
