package com.example.instagram.loginpresentation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.instagram.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextEmail = findViewById(R.id.login_edit_text_email);
        editTextEmail.addTextChangedListener(watcher);

        EditText editTextPassword = findViewById(R.id.login_edit_text_password);
        editTextPassword.addTextChangedListener(watcher);



        findViewById(R.id.login_button_enter).setOnClickListener(v -> {
            TextInputLayout textInputLayoutEmail = findViewById(R.id.login_edit_text_email_input);
            textInputLayoutEmail.setError("Email inválido !");

            TextInputLayout textInputLayoutPassword = findViewById(R.id.login_edit_text_password_input);
            textInputLayoutPassword.setError("Senha Inválida");
        });

    }
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!s.toString().isEmpty()){
                findViewById(R.id.login_button_enter).setEnabled(true);
            }
            else {
                findViewById(R.id.login_button_enter).setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}