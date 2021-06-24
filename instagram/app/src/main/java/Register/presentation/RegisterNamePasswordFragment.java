package Register.presentation;

import android.widget.EditText;
import android.widget.Toast;

import com.example.instagram.R;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import common.view.AbstractFragment;
import common.view.LoadingButton;

public class RegisterNamePasswordFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.NamePasswordView {

    @BindView(R.id.register_edit_text_name_input)
    TextInputLayout nameTextInputLayout;

    @BindView(R.id.register_edit_text_name)
    EditText name;

    @BindView(R.id.register_edit_text_password_input)
    TextInputLayout passwordTextInputLayout;

    @BindView(R.id.register_edit_text_pasword)
    EditText password;

    @BindView(R.id.register_edit_text_password_confirm_input)
    TextInputLayout passwordConfirmTextInputLayout;

    @BindView(R.id.register_edit_text_pasword_confirm)
    EditText confirmPassword;

    @BindView(R.id.register_button_continue)
    LoadingButton buttonContinue;

    public static RegisterNamePasswordFragment newInstance(RegisterPresenter registerPresenter) {

        RegisterNamePasswordFragment fragment = new RegisterNamePasswordFragment();

        fragment.setPresenter(registerPresenter);
        registerPresenter.setNamePasswordView(fragment);

        return fragment;
    }

    @Override
    public void onFailureForm(String nameError, String passwordError) {
        if (nameError != null) {
            nameTextInputLayout.setError(nameError);
            nameTextInputLayout.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
        passwordTextInputLayout.setError(passwordError);
        password.setBackground(findDrawable(R.drawable.edit_text_background_error));
    }


    @OnClick(R.id.register_button_continue)
    public void onButtonNextClick() {
        hideInput(getContext(),confirmPassword);
        presenter.setNameAndPassword(name.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());
    }

    @OnClick(R.id.register_text_view_login)
    public void loginButtonClick() {
        if (isAdded() && getActivity() != null) {
            getActivity().finish();
        }
    }

    @OnTextChanged({R.id.register_edit_text_name, R.id.register_edit_text_pasword, R.id.register_edit_text_pasword_confirm})
    public void onTextChanged(CharSequence s) {

        buttonContinue.setEnabled(!name.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty());
        name.setBackground(findDrawable(R.drawable.edit_text_background));
        nameTextInputLayout.setError(null);
        nameTextInputLayout.setErrorEnabled(false);

        password.setBackground(findDrawable(R.drawable.edit_text_background));
        passwordTextInputLayout.setError(null);
        passwordTextInputLayout.setErrorEnabled(false);

        confirmPassword.setBackground(findDrawable(R.drawable.edit_text_background));
        passwordConfirmTextInputLayout.setError(null);
        passwordConfirmTextInputLayout.setErrorEnabled(false);

    }

    @Override
    public void onFailureCreateUser(String messageError) {
        hideInput(getContext(), confirmPassword);
        Toast.makeText(getContext(), messageError, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        buttonContinue.showProgressBar(true);
    }

    @Override
    public void hideProgressBar() {
        buttonContinue.showProgressBar(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_name_password;
    }
}
