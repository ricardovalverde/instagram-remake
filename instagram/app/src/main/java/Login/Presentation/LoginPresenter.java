package Login.Presentation;

import android.os.Handler;

import com.example.instagram.R;

import Common.presenter.Presenter;
import Common.util.Strings;
import Login.DataSource.LoginDataSource;

class LoginPresenter implements Presenter {

     private final LoginView view;
     private final LoginDataSource dataSource;

    public LoginPresenter(LoginView loginView, LoginDataSource dataSource) {
        this.view = loginView;
        this.dataSource = dataSource;
    }
    void login(String email, String password){
        if(!Strings.emailValid(email)){
            view.onFailureForm(view.getContext().getString(R.string.invalid_email),null);
            return;
        }

        view.showProgress();
        dataSource.login(email,password,this);



    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String messageError) {
    view.onFailureForm(null,messageError);
    }

    @Override
    public void onComplete() {
        view.hideProgress();
    }
}
