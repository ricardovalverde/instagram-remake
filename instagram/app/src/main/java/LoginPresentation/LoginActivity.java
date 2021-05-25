package LoginPresentation;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.instagram.R;
import com.google.android.material.textfield.TextInputLayout;

import Common.View.LoadingButton;

public class LoginActivity extends AppCompatActivity {
    private LoadingButton button_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextEmail = findViewById(R.id.login_edit_text_email);
        editTextEmail.addTextChangedListener(watcher);

        EditText editTextPassword = findViewById(R.id.login_edit_text_password);
        editTextPassword.addTextChangedListener(watcher);

        button_enter = findViewById(R.id.login_button_enter);
        button_enter.setOnClickListener(v -> {

            button_enter.showProgressBar(true);

            new Handler().postDelayed(() -> {
                button_enter.showProgressBar(false);
                TextInputLayout textInputLayoutEmail = findViewById(R.id.login_edit_text_email_input);
                textInputLayoutEmail.setError("Email inválido !");

                TextInputLayout textInputLayoutPassword = findViewById(R.id.login_edit_text_password_input);
                textInputLayoutPassword.setError("Senha Inválida");

            }, 4000);


        });


    }

    private final TextWatcher watcher = new TextWatcher() {
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
    };

}