package Register.presentation;

import android.widget.EditText;

import com.example.instagram.R;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import common.component.LoadingButton;
import common.view.AbstractFragment;

public class RegisterEmailFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.EmailView {

    @BindView(R.id.register_edit_text_email_input)
    TextInputLayout textInputLayoutEmail;

    @BindView(R.id.register_edit_text_email)
    EditText editTextEmail;

    @BindView(R.id.register_email_button_next)
    LoadingButton buttonNext;

    public static RegisterEmailFragment newInstance(RegisterPresenter registerPresenter) {

        RegisterEmailFragment fragment = new RegisterEmailFragment();
        fragment.setPresenter(registerPresenter);
        registerPresenter.setEmailView(fragment);

        return fragment;
    }

    @Override
    public void onFailureForm(String emailError) {
        textInputLayoutEmail.setError(emailError);
        editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background_error));
    }

    @OnClick(R.id.register_email_button_next)
    public void onButtonNextClick() {
        presenter.setEmail(editTextEmail.getText().toString());
    }

    @OnClick(R.id.register_text_view_login)
    public void onTextViewLoginClick() {
        if (isAdded() && getActivity() != null) {
            getActivity().finish();
        }
    }

    @OnTextChanged(R.id.register_edit_text_email)
    public void onTextChanged(CharSequence s) {
        buttonNext.setEnabled(!editTextEmail.getText().toString().isEmpty());
        editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background));
        textInputLayoutEmail.setError(null);
        textInputLayoutEmail.setErrorEnabled(false);
    }

    @Override
    public void showProgressBar() {
        buttonNext.showProgressBar(true);
    }

    @Override
    public void hideProgressBar() {
        buttonNext.showProgressBar(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_email;
    }
}
