package Login.Presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.instagram.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import Login.DataSource.LoginDataSource;
import Login.DataSource.LoginFireBaseDataSource;
import Main.Presentation.MainActivity;
import Register.presentation.RegisterActivity;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import common.component.LoadingButton;
import common.view.AbstractActivity;

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

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();

        String userAuth = FirebaseAuth.getInstance().getUid();
        if (userAuth != null) {
            onUserLogged();
        }
    }

    @Override
    protected void onInject() {
        LoginDataSource dataSource = new LoginFireBaseDataSource();
        presenter = new LoginPresenter(this, dataSource);
    }

    @Override
    public void onUserLogged() {
        MainActivity.launch(this, MainActivity.LOGIN_ACTIVITY);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
    public void showProgressBar() {
        button_enter.showProgressBar(true);
    }

    @Override
    public void hideProgressBar() {
        button_enter.showProgressBar(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }
}