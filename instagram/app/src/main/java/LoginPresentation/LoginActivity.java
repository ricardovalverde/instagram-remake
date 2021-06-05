package LoginPresentation;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.instagram.R;
import com.google.android.material.textfield.TextInputLayout;

import Common.view.AbstractActivity;
import Common.view.LoadingButton;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends AbstractActivity implements LoginView, TextWatcher {

    @BindView(R.id.login_edit_text_email)
    EditText editTextEmail;
    @BindView(R.id.login_edit_text_email_input)
    TextInputLayout inputLayoutEmail;

    @BindView(R.id.login_edit_text_password)
    EditText editTextPassword;
    @BindView(R.id.login_edit_text_password_input)
    TextInputLayout inputLayoutPassword;

    @BindView(R.id.login_button_enter)
    LoadingButton button_enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));

        editTextEmail.addTextChangedListener(this);
        editTextPassword.addTextChangedListener(this);


    }

    @Override
    public void showProgress() {
        button_enter.showProgressBar(true);
    }

    @Override
    public void hideProgress() {
        button_enter.showProgressBar(false);
    }

    @OnClick(R.id.login_button_enter)
    public void onButtonEnterClick() {

        button_enter.showProgressBar(true);

        new Handler().postDelayed(() -> {
            button_enter.showProgressBar(false);
        }, 4000);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        button_enter.setEnabled(!s.toString().isEmpty());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


    @Override
    public void onFailureForm(String emailError, String passwordError) {
        if (emailError != null) {
            inputLayoutEmail.setError(emailError);
            //editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
        if (passwordError != null) {
            inputLayoutPassword.setError(emailError);
            //editTextPassword.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }

    }

    @Override
    public void onUserLogged() {
        // TODO: 04/06/2021

    }
}