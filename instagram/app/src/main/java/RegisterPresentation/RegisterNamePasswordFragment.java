package RegisterPresentation;

import android.widget.EditText;

import com.example.instagram.R;
import com.google.android.material.textfield.TextInputLayout;

import common.view.AbstractFragment;
import common.view.LoadingButton;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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

    @BindView(R.id.register_button_next)
    LoadingButton buttonNext;

    public static RegisterNamePasswordFragment newInstance(RegisterPresenter registerPresenter) {
        RegisterNamePasswordFragment fragment = new RegisterNamePasswordFragment();
        fragment.setPresenter(registerPresenter);
        registerPresenter.setNamePasswordView(fragment);

        return fragment;
    }

    @OnClick(R.id.register_button_next)
    public void setButtonNextClick() {
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
        buttonNext.setEnabled(!name.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty());
        name.setBackground(findDrawble(R.drawable.edit_text_background));
        nameTextInputLayout.setError(null);
        nameTextInputLayout.setErrorEnabled(false);

        password.setBackground(findDrawble(R.drawable.edit_text_background));
        passwordTextInputLayout.setError(null);
        passwordTextInputLayout.setErrorEnabled(false);

        confirmPassword.setBackground(findDrawble(R.drawable.edit_text_background));
        passwordConfirmTextInputLayout.setError(null);
        passwordConfirmTextInputLayout.setErrorEnabled(false);

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_name_password;
    }

    @Override
    public void onFailureForm(String nameError, String passwordError) {
        if (nameError != null) {
            nameTextInputLayout.setError(nameError);
            nameTextInputLayout.setBackground(findDrawble(R.drawable.edit_text_background_error));
        }
        passwordTextInputLayout.setError(passwordError);
        passwordTextInputLayout.setBackground(findDrawble(R.drawable.edit_text_background_error));
    }

    @Override
    public void showProgress() {
        buttonNext.showProgressBar(true);
    }

    @Override
    public void hideProgress() {
        buttonNext.showProgressBar(false);
    }
}
