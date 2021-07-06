package Main.Profile.DataSource;

import common.model.UserProfile;
import common.presenter.Presenter;

public interface ProfileDataSource {
    void findUser(Presenter<UserProfile> profilePresenter);
}
