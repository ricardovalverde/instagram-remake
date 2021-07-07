package Main.Profile.DataSource;

import Main.Profile.Presentation.ProfilePresenter;
import common.model.UserProfile;
import common.presenter.Presenter;

public interface ProfileDataSource {
    void findUser(Presenter<UserProfile> profilePresenter);
}
