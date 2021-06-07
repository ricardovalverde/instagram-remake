package Login.DataSource;

import Common.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {
    @Override
    public void login(String email, String password, Presenter presenter) {
        presenter.onError("error1");
        presenter.onComplete();

    }
}
