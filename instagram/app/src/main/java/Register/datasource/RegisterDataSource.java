package Register.datasource;

import common.presenter.Presenter;

public interface RegisterDataSource {
    void createUser(String name, String email, String password, Presenter presenter);
}
