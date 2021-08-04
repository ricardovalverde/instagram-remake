package Main.Profile.Presentation;

import java.util.List;

import Main.Presentation.MainView;
import Main.Profile.DataSource.ProfileDataSource;
import common.model.Database;
import common.model.Post;
import common.model.User;
import common.model.UserProfile;
import common.presenter.Presenter;

public class ProfilePresenter implements Presenter<UserProfile> {

    private final ProfileDataSource profileDataSource;
    private MainView.ProfileView view;

    public ProfilePresenter(ProfileDataSource profileDataSource) {
        this.profileDataSource = profileDataSource;
    }

    public void setView(MainView.ProfileView view) {
        this.view = view;
    }


    public void findUser(String user) {

        view.showProgressBar();
        profileDataSource.findUser(user, this);

    }


    @Override
    public void onSuccess(UserProfile response) {
        User user = response.getUser();
        List<Post> posts = response.getPosts();
        boolean editProfile = user.getUuid().equals(Database.getINSTANCE().getUser().getUserId());

        view.showData(user.getName(),
                String.valueOf(user.getFollowing()),
                String.valueOf(user.getFollowers()),
                String.valueOf(user.getPost()),
                editProfile);

        view.showPosts(posts);

        if (user.getUri() != null) {
            view.showPhoto(user.getUri());
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
