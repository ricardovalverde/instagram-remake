package RegisterPresentation;

import android.content.Context;

public interface RegisterView {
    interface EmailView{
        Context getContext();
        void onFailureForm(String email);
        void showNextView();
    }
}
