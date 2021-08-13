package Register.presentation;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.instagram.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import common.component.CustomDialog;
import common.component.LoadingButton;
import common.view.AbstractFragment;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {
    @BindView(R.id.register_button_next)
    LoadingButton buttonNext;

    @BindView(R.id.register_image_icon)
    ImageView imageViewCropped;

    public static RegisterPhotoFragment newInstance(RegisterPresenter registerPresenter) {
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();
        fragment.setPresenter(registerPresenter);
        registerPresenter.setPhotoView(fragment);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNext.setEnabled(true);
    }

    @Override
    public void onImageCropped(Uri uri) {
        try {
            if (getContext() != null && getContext().getContentResolver() != null) {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                imageViewCropped.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            Log.e("Teste", e.getMessage());
        }

    }

    @OnClick(R.id.register_button_next)
    public void onButtonNextClick() {
        CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle(R.string.define_photo_profile)
                .addButton((v) -> {
                    switch (v.getId()) {
                        case R.string.take_picture:
                            presenter.showCamera();
                            break;
                        case R.string.search_gallery:
                            presenter.showGallery();
                            break;
                    }
                }, R.string.take_picture, R.string.search_gallery)
                .build();
        customDialog.show();

    }

    @OnClick(R.id.register_button_jump)
    public void onButtonJumpClick() {
        presenter.jumpRegistration();
    }

    @Override
    public void showProgressBar() {
        buttonNext.showProgressBar(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_photo;
    }
}
