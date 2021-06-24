package Register.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.instagram.R;

import Main.MainActivity;
import Register.datasource.RegisterDataSource;
import Register.datasource.RegisterLocalDataSource;
import butterknife.BindView;
import common.view.AbstractActivity;

public class RegisterActivity extends AbstractActivity implements RegisterView {

    private RegisterPresenter registerPresenter;

    @BindView(R.id.register_scrollView)
    ScrollView scrollView;

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
        Fragment fragment = null;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scrollView.getLayoutParams();

        switch (step) {
            case EMAIL:
                layoutParams.gravity = Gravity.BOTTOM;
                fragment = RegisterEmailFragment.newInstance(registerPresenter);
                break;
            case NAME_PASSWORD:
                layoutParams.gravity = Gravity.BOTTOM;
                fragment = RegisterNamePasswordFragment.newInstance(registerPresenter);
                break;
            case WELCOME:
                layoutParams.gravity = Gravity.BOTTOM;
                fragment = RegisterWelcomeFragment.newInstance(registerPresenter);
                break;
            case PHOTO:
                layoutParams.gravity = Gravity.TOP;
                fragment = RegisterPhotoFragment.newInstance(registerPresenter);
        }
        scrollView.setLayoutParams(layoutParams);

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

    @Override
    public void onUserCreated() {
        MainActivity.launch(this);
    }
}