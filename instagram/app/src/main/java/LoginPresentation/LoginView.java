package LoginPresentation;

public interface LoginView {

    void onFailureForm(String emailError, String passwordError);

    void onUserLogged();
}
