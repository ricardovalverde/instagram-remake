package Main.Camera.Presentation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.instagram.R;

import butterknife.BindView;
import butterknife.OnClick;
import common.component.CameraPreview;
import common.component.MediaHelper;
import common.view.AbstractFragment;

public class CameraFragment extends AbstractFragment {

    @BindView(R.id.camera_progress)
    ProgressBar progressBar;

    @BindView(R.id.camera_surface)
    FrameLayout frameLayoutCamera;

    @BindView(R.id.container_preview)
    LinearLayout containerPreview;

    @BindView(R.id.camera_button_image_view_picture)
    Button buttonPicture;

    private AddView addView;
    private MediaHelper mediaHelper;
    private Camera camera;

    public static CameraFragment newInstance(AddView addView) {
        CameraFragment cameraFragment = new CameraFragment();
        cameraFragment.setAddView(addView);
        return cameraFragment;
    }

    private void setAddView(AddView addView) {
        this.addView = addView;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);


        if (getContext() != null) {
            mediaHelper = MediaHelper.getINSTANCE(this);

            if (mediaHelper.checkCameraHardware(getContext())) {
                camera = mediaHelper.getCameraInstance();
                CameraPreview cameraPreview = new CameraPreview(getContext(), camera);
                frameLayoutCamera.addView(cameraPreview);

            }
        }
        return view;
    }

    @OnClick(R.id.camera_button_image_view_picture)
    public void onCameraButtonClick() {
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);
        buttonPicture.setVisibility(View.GONE);

        camera.takePicture(null, null, (bytes, camera) -> {
            progressBar.setVisibility(View.GONE);
            buttonPicture.setVisibility(View.VISIBLE);

            Uri uri = mediaHelper.saveCameraFile(bytes);
            if(uri != null){
                addView.onImageLoaded(uri);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.release();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_camera;
    }
}
