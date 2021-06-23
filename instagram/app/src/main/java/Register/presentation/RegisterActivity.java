package Register.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.instagram.R;

import Register.datasource.RegisterDataSource;
import Register.datasource.RegisterLocalDataSource;
import common.view.AbstractActivity;

public class RegisterActivity extends AbstractActivity implements RegisterView {

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onInject() {
        RegisterDataSource dataSource = new RegisterLocalDataSource();
        registerPresenter = new RegisterPresenter(dataSource);
        registerPresenter.setRegisterView(this);

        showNextView(RegisterSteps.EMAIL);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void showNextView(RegisterSteps step) {
        Fragment fragment = RegisterEmailFragment.newInstance(registerPresenter);

        switch (step) {
            case EMAIL:
                break;
            case NAME_PASSWORD:
                fragment = RegisterNamePasswordFragment.newInstance(registerPresenter);
                break;
            case WELCOME:
                fragment = RegisterWelcomeFragment.newInstance(registerPresenter);
                break;
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (manager.findFragmentById(R.id.register_fragment) == null) {
            transaction.add(R.id.register_fragment, fragment, step.name());
        } else {
            transaction.replace(R.id.register_fragment, fragment, step.name());
            transaction.addToBackStack(step.name());
        }
        transaction.commit();
    }
}