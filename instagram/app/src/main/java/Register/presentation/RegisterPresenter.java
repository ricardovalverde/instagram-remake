package Register.presentation;

import android.net.Uri;

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
    private RegisterView.PhotoView photoView;

    private String email;
    private String name;
    private Uri uri;

    public RegisterPresenter(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getName() {
        return name;
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

    public void setPhotoView(RegisterView.PhotoView photoView) {
        this.photoView = photoView;
    }

    public void showPhotoView() {
        registerView.showNextView(RegisterSteps.PHOTO);
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        if (uri != null) {
            photoView.onImageCropped(uri);
            photoView.showProgressBar();
            dataSource.addPhoto(uri, new UpdatePhotoCallback());
        }
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

        namePasswordView.showProgressBar();
        dataSource.createUser(name, email, password, this);

    }

    public void jumpRegistration() {
        registerView.onUserCreated();
    }

    public void showCamera() {
        registerView.showCamera();
    }

    public void showGallery() {
        registerView.showGallery();
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

    private class UpdatePhotoCallback implements Presenter<Boolean> {

        @Override
        public void onSuccess(Boolean response) {
            registerView.onUserCreated();
        }

        @Override
        public void onError(String messageError) {

        }

        @Override
        public void onComplete() {

        }
    }
}
