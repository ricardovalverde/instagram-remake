package Login.Presentation;

import common.view.MainView;

public interface LoginView extends MainView {

    void onFailureForm(String emailError, String passwordError);

    void onUserLogged();
}
