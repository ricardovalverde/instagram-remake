package Register.presentation;

import android.content.Context;

import common.view.MainView;

public interface RegisterView {

    void showNextView(RegisterSteps step);

    interface EmailView {
        Context getContext();

        void onFailureForm(String email);
    }

    interface NamePasswordView extends MainView {
        Context getContext();

        void onFailureForm(String nameError, String passwordError);

        void onFailureCreateUser(String messageError);
    }

    interface WelcomeView {
    }
}
