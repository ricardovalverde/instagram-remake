package RegisterPresentation;

import com.example.instagram.R;

import Common.util.Strings;

public class RegisterPresenter {

    private RegisterView.EmailView emailView;
    private String email;

    public void setEmailView(RegisterView.EmailView emailView) {
        this.emailView = emailView;
    }

    public void setEmail(String email){
        if(!Strings.emailValid(email)){
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_email));
            return;
        }
        this.email = email;
    }
}
