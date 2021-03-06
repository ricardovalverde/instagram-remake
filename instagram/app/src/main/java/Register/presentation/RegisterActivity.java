package Register.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.canhub.cropper.CropImageView;
import com.example.instagram.R;

import Main.Presentation.MainActivity;
import Register.datasource.RegisterDataSource;
import Register.datasource.RegisterFirebaseDataSource;
import butterknife.BindView;
import butterknife.OnClick;
import common.component.MediaHelper;
import common.view.AbstractActivity;

public class RegisterActivity extends AbstractActivity implements RegisterView, MediaHelper.OnImageCropped {

    @BindView(R.id.register_root_container)
    FrameLayout rootContainer;

    @BindView(R.id.register_crop_image_view)
    CropImageView cropImageView;

    @BindView(R.id.register_button_crop)
    Button buttonCrop;

    @BindView(R.id.register_scrollView)
    ScrollView scrollView;

    private RegisterPresenter registerPresenter;
    private MediaHelper mediaHelper;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onInject() {
        RegisterDataSource dataSource = new RegisterFirebaseDataSource();
        registerPresenter = new RegisterPresenter(dataSource);
        registerPresenter.setRegisterView(this);

        showNextView(RegisterSteps.EMAIL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();

        mediaHelper = MediaHelper.getINSTANCE(this)
                .setCropImageView(cropImageView)
                .setListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropViewEnabled(true);
        MediaHelper mediaHelper = MediaHelper.getINSTANCE(this);
        mediaHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showCamera();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onUserCreated() {
        MainActivity.launch(this, MainActivity.REGISTER_ACTIVITY);
    }

    @Override
    public void showCamera() {
        mediaHelper.chooserCamera();
    }

    @Override
    public void showGallery() {
        mediaHelper.chooserGallery();
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

    private void cropViewEnabled(boolean enabled) {
        cropImageView.setVisibility(enabled ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(enabled ? View.GONE : View.VISIBLE);
        buttonCrop.setVisibility(enabled ? View.VISIBLE : View.GONE);
        rootContainer.setBackgroundColor(enabled ? findColor(android.R.color.black) : findColor(android.R.color.white));
    }

    @Override
    public void onImageCropped(Uri uri) {
        registerPresenter.setUri(uri);
    }

    @Override
    public void onImagePicked(Uri uri) {
        cropImageView.setImageUriAsync(uri);
    }

    @OnClick(R.id.register_button_crop)
    public void onButtonCropClick() {
        cropViewEnabled(false);
        MediaHelper.getINSTANCE(this)
                .cropImage(cropImageView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }
}