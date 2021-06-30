package common.component;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class MediaHelper {

    private static MediaHelper INSTANCE;

    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;

    private Activity activity;
    private Fragment fragment;

    private Uri mCropImageUri;
    private Uri mSavedImageUri;

    private CropImageView cropImageView;
    private OnImageCropped listener;


    private Context getContext() {
        if (fragment != null && fragment.getActivity() != null) {
            return fragment.getActivity();
        }
        return activity;
    }

    public static MediaHelper getINSTANCE(Activity activity) {
        if (INSTANCE == null) {
            INSTANCE = new MediaHelper();
            INSTANCE.setActivity(activity);

        }
        return INSTANCE;
    }

    public static MediaHelper getINSTANCE(Fragment fragment) {
        if (INSTANCE == null) {
            INSTANCE = new MediaHelper();
            INSTANCE.setFragment(fragment);
        }
        return INSTANCE;
    }

    public MediaHelper setCropImageView(CropImageView cropImageView) {
        this.cropImageView = cropImageView;
        cropImageView.setAspectRatio(1, 1);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setOnCropImageCompleteListener((view, cropResult) -> {
            Uri uri = cropResult.getUriContent();
            if (uri != null && listener != null) {
                listener.onImageCropped(uri);
                cropImageView.setVisibility(View.GONE);
            }
        });

        return this;
    }

    public MediaHelper setListener(OnImageCropped listener) {
        this.listener = listener;
        return this;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), mCropImageUri)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (activity != null) {
                        activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                    } else {
                        fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                    }
                }
            } else {
                startCropImageActivity();
            }

        } else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            mCropImageUri = CropImage.getPickImageResultUriContent(getContext(), data);
            startCropImageActivity();
        }
    }

    private void startCropImageActivity() {
        cropImageView.setImageUriAsync(mCropImageUri);
    }

    public void cropImage() {
        File getImage = getContext().getExternalCacheDir();
        if (getImage != null) {
            mSavedImageUri = Uri.fromFile(new File(getImage.getPath(), System.currentTimeMillis() + ".jpeg"));
        }
        cropImageView.saveCroppedImageAsync(mSavedImageUri, Bitmap.CompressFormat.JPEG, 90, 0, 0, CropImageView.RequestSizeOptions.NONE);
    }

    public void chooserGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, GALLERY_CODE);
    }

    public void chooserCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                mCropImageUri = FileProvider.getUriForFile(getContext(), "com.example.instagram.fileprovider", photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, mCropImageUri);
                activity.startActivityForResult(i, CAMERA_CODE);
            }
        }

    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpeg", storageDir);
    }

    public interface OnImageCropped {
        void onImageCropped(Uri uri);
    }

}
