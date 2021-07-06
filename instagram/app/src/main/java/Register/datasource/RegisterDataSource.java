package Register.datasource;

import android.net.Uri;

import common.presenter.Presenter;

public interface RegisterDataSource {
    void createUser(String name, String email, String password, Presenter presenter);

    void addPhoto(Uri uri, Presenter presenter);
}
