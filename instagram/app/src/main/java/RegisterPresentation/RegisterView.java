package RegisterPresentation;

import android.content.Context;

public interface RegisterView {

    void showNextView(RegisterSteps step);

    interface EmailView {
        Context getContext();

        void onFailureForm(String email);

    }

    interface NamePasswordView {
        Context getContext();

        void onFailureForm(String nameError, String passwordError);

    }
}
