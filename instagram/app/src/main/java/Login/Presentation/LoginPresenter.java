package Login.Presentation;

import com.example.instagram.R;
import com.google.firebase.auth.FirebaseUser;

import Login.DataSource.LoginDataSource;
import common.presenter.Presenter;
import common.util.Strings;

class LoginPresenter implements Presenter<FirebaseUser> {

    private final LoginView view;
    private final LoginDataSource dataSource;

    public LoginPresenter(LoginView loginView, LoginDataSource dataSource) {
        this.view = loginView;
        this.dataSource = dataSource;
    }

    void login(String email, String password) {
        if (!Strings.emailValid(email)) {
            view.onFailureForm(view.getContext().getString(R.string.invalid_email), null);
            return;
        }
        view.showProgressBar();
        dataSource.login(email, password, this);
    }

    @Override
    public void onSuccess(FirebaseUser userAuth) {
        view.onUserLogged();
    }

    @Override
    public void onError(String messageError) {
        view.onFailureForm(null, messageError);
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
