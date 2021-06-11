package Login.DataSource;

import Common.presenter.Presenter;

public interface LoginDataSource {
    void login(String email, String password, Presenter presenter);
}
