package Login.Presentation;

import Common.view.View;

public interface LoginView extends View {

    void onFailureForm(String emailError, String passwordError);

    void onUserLogged();
}
