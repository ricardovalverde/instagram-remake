package Register.presentation;

import com.example.instagram.R;

import Register.datasource.RegisterDataSource;
import common.model.UserAuth;
import common.presenter.Presenter;
import common.util.Strings;

public class RegisterPresenter implements Presenter<UserAuth> {

    private final RegisterDataSource dataSource;
    private RegisterView registerView;
    private RegisterView.EmailView emailView;
    private RegisterView.NamePasswordView namePasswordView;
    private RegisterView.WelcomeView welcomeView;

    private String email;
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public RegisterPresenter(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setRegisterView(RegisterView registerView) {
        this.registerView = registerView;
    }

    public void setEmailView(RegisterView.EmailView emailView) {
        this.emailView = emailView;
    }

    public void setNamePasswordView(RegisterView.NamePasswordView namePasswordView) {
        this.namePasswordView = namePasswordView;
    }

    public void setWelcomeView(RegisterView.WelcomeView welcomeView) {
        this.welcomeView = welcomeView;
    }

    public void setEmail(String email) {
        if (!Strings.emailValid(email)) {
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_email));
            return;
        }
        this.email = email;

        registerView.showNextView(RegisterSteps.NAME_PASSWORD);
    }

    public void setNameAndPassword(String name, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            namePasswordView.onFailureForm(null, namePasswordView.getContext().getString(R.string.password_not_equal));
            return;
        }
        this.name = name;
        this.password = password;

        namePasswordView.showProgressBar();
        dataSource.createUser(this.name, this.email, this.password, this);

    }

    @Override
    public void onSuccess(UserAuth response) {
        registerView.showNextView(RegisterSteps.WELCOME);
    }

    @Override
    public void onError(String messageError) {
        namePasswordView.onFailureCreateUser(messageError);
    }

    @Override
    public void onComplete() {
        namePasswordView.hideProgressBar();
    }
}
