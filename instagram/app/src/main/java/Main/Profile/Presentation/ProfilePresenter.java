package Main.Profile.Presentation;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import Main.Presentation.MainView;
import Main.Profile.DataSource.ProfileDataSource;
import common.model.Post;
import common.model.User;
import common.model.UserProfile;
import common.presenter.Presenter;

public class ProfilePresenter implements Presenter<UserProfile> {

    private final ProfileDataSource profileDataSource;
    private final String user;
    private MainView.ProfileView view;

    public ProfilePresenter(ProfileDataSource profileDataSource, String user) {
        this.profileDataSource = profileDataSource;
        this.user = user;
    }

    public ProfilePresenter(ProfileDataSource profileDataSource) {
        this(profileDataSource, FirebaseAuth.getInstance().getUid());
    }

    public void setView(MainView.ProfileView view) {
        this.view = view;
    }

    public void follow(boolean follow) {
        if (follow) {
            profileDataSource.follow(user);
        } else {
            profileDataSource.unfollow(user);
        }
    }

    public void findUser() {

        view.showProgressBar();
        profileDataSource.findUser(user, this);

    }

    public String getUser() {
        return user;
    }

    @Override
    public void onSuccess(UserProfile userProfile) {
        User user = userProfile.getUser();
        List<Post> posts = userProfile.getPosts();
        boolean editProfile = user.getUuid().equals(FirebaseAuth.getInstance().getUid());

        view.showData(user.getName(),
                String.valueOf(user.getFollowing()),
                String.valueOf(user.getFollowers()),
                String.valueOf(user.getPost()),
                editProfile,
                userProfile.isFollowing());

        view.showPosts(posts);

        if (user.getUrlPhoto() != null) {
            view.showPhoto(user.getUrlPhoto());
        }

    }

    @Override
    public void onError(String messageError) {

    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
