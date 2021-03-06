package Main.Profile.DataSource;

import common.model.UserProfile;
import common.presenter.Presenter;

public interface ProfileDataSource {
    void findUser(String user, Presenter<UserProfile> profilePresenter);

    void follow(String user);

    void unfollow(String user);

}
