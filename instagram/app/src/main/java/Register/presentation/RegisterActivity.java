package Register.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.canhub.cropper.CropImageView;
import com.example.instagram.R;

import Main.MainActivity;
import Register.datasource.RegisterDataSource;
import Register.datasource.RegisterLocalDataSource;
import butterknife.BindView;
import butterknife.OnClick;
import common.view.AbstractActivity;

public class RegisterActivity extends AbstractActivity implements RegisterView {

    private RegisterPresenter registerPresenter;

    @BindView(R.id.register_root_container)
    FrameLayout rootContainer;

    @BindView(R.id.register_crop_image_view)
    CropImageView cropImageView;

    @BindView(R.id.register_button_crop)
    Button buttonCrop;


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

        switch (step) {
            case EMAIL:
                fragment = RegisterEmailFragment.newInstance(registerPresenter);
                break;
            case NAME_PASSWORD:
                fragment = RegisterNamePasswordFragment.newInstance(registerPresenter);
                break;
            case WELCOME:
                fragment = RegisterWelcomeFragment.newInstance(registerPresenter);
                break;
            case PHOTO:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) scrollView.getLayoutParams();
                layoutParams.gravity = Gravity.TOP;
                scrollView.setLayoutParams(layoutParams);
                fragment = RegisterPhotoFragment.newInstance(registerPresenter);
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

    @Override
    public void onUserCreated() {
        MainActivity.launch(this);
    }


    @Override
    public void showCamera() {

    }

    @Override
    public void showGallery() {

    }
    @OnClick(R.id.register_button_crop)
    public void onButtonCropClick(){

    }
}