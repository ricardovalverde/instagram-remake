package Login.DataSource;

import Common.presenter.Presenter;
import Common.util.Strings;

public interface LoginDataSource {
    void login(String email, String password, Presenter presenter);
}
