package RegisterPresentation;

import android.widget.EditText;

import com.example.instagram.R;
import com.google.android.material.textfield.TextInputLayout;

import Common.util.Strings;
import Common.view.AbstractFragment;
import Common.view.LoadingButton;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterEmailFragment extends AbstractFragment implements RegisterView.EmailView {
    @BindView(R.id.register_edit_text_email_input)
    TextInputLayout textInputLayoutEmail;

    @BindView(R.id.register_edit_text_email)
    EditText editTextEmail;

    @BindView(R.id.register_button_next)
    LoadingButton buttonNext;


    @Override
    protected int getLayout() {
        return R.layout.fragment_register_email;
    }

    @Override
    public void onFailureForm(String email) {


    }

    @OnClick(R.id.register_button_next)
    public void onButtonNextClick() {


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
        editTextEmail.setBackground(findDrawble(R.drawable.edit_text_background));
        textInputLayoutEmail.setError(null);
        textInputLayoutEmail.setErrorEnabled(false);
    }

}
