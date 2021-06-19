package Login.DataSource;

import common.presenter.Presenter;

public interface LoginDataSource {
    void login(String email, String password, Presenter presenter);
}
