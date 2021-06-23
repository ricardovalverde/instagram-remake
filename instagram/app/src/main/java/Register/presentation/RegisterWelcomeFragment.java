package Register.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.instagram.R;

import butterknife.BindView;
import common.view.AbstractFragment;
import common.view.LoadingButton;

public class RegisterWelcomeFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.WelcomeView {

    @BindView(R.id.register_button_next)
    LoadingButton buttonNext;

    @BindView(R.id.text_view_welcome)
    TextView textWelcome;


    public static RegisterWelcomeFragment newInstance(RegisterPresenter registerPresenter) {
        RegisterWelcomeFragment welcomeFragment = new RegisterWelcomeFragment();
        welcomeFragment.setPresenter(registerPresenter);
        registerPresenter.setWelcomeView(welcomeFragment);

        return welcomeFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNext.setEnabled(true);
        textWelcome.setText(getString(R.string.welcome_to_instagram, presenter.getName()));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_welcome;
    }
}
