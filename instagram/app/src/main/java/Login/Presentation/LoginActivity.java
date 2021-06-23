package Login.Presentation;

import android.os.Bundle;
import android.widget.EditText;

import com.example.instagram.R;
import com.google.android.material.textfield.TextInputLayout;

import common.view.AbstractActivity;
import common.view.LoadingButton;
import Login.DataSource.LoginDataSource;
import Login.DataSource.LoginLocalDataSource;
import Main.MainActivity;
import Register.presentation.RegisterActivity;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AbstractActivity implements LoginView {

    public LoginPresenter presenter;

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
        setStatusBarDark();
    }

    @Override
    public void showProgressBar() {
        button_enter.showProgressBar(true);
    }

    @Override
    public void hideProgressBar() {
        button_enter.showProgressBar(false);
    }

    @Override
    protected void onInject() {
        LoginDataSource dataSource = new LoginLocalDataSource();
        presenter = new LoginPresenter(this, dataSource);
    }

    @OnClick(R.id.login_button_enter)
    public void onButtonEnterClick() {
        presenter.login(editTextEmail.getText().toString(), editTextPassword.getText().toString());
    }

    @OnClick({R.id.login_text_view_register})
    public void onTextViewRegisterClick() {
        RegisterActivity.launch(this);
    }

    @OnTextChanged({R.id.login_edit_text_email, R.id.login_edit_text_password})
    public void onTextChanged(CharSequence s) {

        button_enter.setEnabled(!editTextEmail.getText().toString().isEmpty() && !editTextPassword.getText().toString().isEmpty());

        if (s.hashCode() == editTextEmail.getText().hashCode()) {
            inputLayoutEmail.setBackground(findDrawable(R.drawable.edit_text_background));
            inputLayoutEmail.setError(null);
            inputLayoutEmail.setErrorEnabled(false);

        } else if (s.hashCode() == editTextPassword.getText().hashCode()) {
            inputLayoutPassword.setBackground(findDrawable(R.drawable.edit_text_background));
            inputLayoutPassword.setError(null);
            inputLayoutPassword.setErrorEnabled(false);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


    @Override
    public void onFailureForm(String emailError, String passwordError) {
        if (emailError != null) {
            inputLayoutEmail.setError(emailError);
            editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
        if (passwordError != null) {
            inputLayoutPassword.setError(passwordError);
            editTextPassword.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
    }

    @Override
    public void onUserLogged() {
        MainActivity.launch(this);
    }
}