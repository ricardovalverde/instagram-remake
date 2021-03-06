package common.component;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MediaHelper {

    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;
    private static WeakReference<MediaHelper> INSTANCE;
    private OnImageCropped listener;
    private Activity activity;
    private Fragment fragment;

    private Uri mCropImageUri;
    private Uri mSavedImageUri;

    private Context getContext() {
        if (fragment != null && fragment.getActivity() != null) {
            return fragment.getActivity();
        }
        return activity;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public static MediaHelper getINSTANCE(Activity activity) {
        if (INSTANCE == null) {

            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setActivity(activity);
        } else if (INSTANCE.get() == null) {

            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setActivity(activity);
        }
        return INSTANCE.get();
    }

    public static MediaHelper getINSTANCE(Fragment fragment) {
        if (INSTANCE == null) {

            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setFragment(fragment);
        } else if (INSTANCE.get() == null) {

            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setFragment(fragment);
        }
        return INSTANCE.get();
    }

    public void cropImage(CropImageView cropImageView) {

        File getImage = getContext().getExternalCacheDir();
        if (getImage != null) {
            mSavedImageUri = Uri.fromFile(new File(getImage.getPath(), System.currentTimeMillis() + ".jpeg"));
        }
        cropImageView.saveCroppedImageAsync(mSavedImageUri, Bitmap.CompressFormat.JPEG, 90, 0, 0, CropImageView.RequestSizeOptions.NONE);
    }

    public MediaHelper setCropImageView(CropImageView cropImageView) {
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

    private static Bitmap rotate(Bitmap bitmap, int degree) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreferences myPrefs = getContext().getSharedPreferences("camera_image", 0);
        String url = myPrefs.getString("url", null);

        if (mCropImageUri == null && url != null) {
            mCropImageUri = Uri.parse(url);
        }

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
                listener.onImagePicked(mCropImageUri);
            }

        } else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            mCropImageUri = CropImage.getPickImageResultUriContent(getContext(), data);
            listener.onImagePicked(mCropImageUri);
        }
    }

    public boolean checkCameraHardware(Context context) {

        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public Camera getCameraInstance(Fragment fragment, Context context) {
        Camera camera = null;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && fragment != null && (context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                fragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, 300);
                return null;
            }
            camera = Camera.open();

        } catch (Exception e) {
            Log.i("CameraInstance", "Error get Camera Instance");
        }
        return camera;
    }

    public void chooserGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, GALLERY_CODE);
    }

    public void chooserCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext() != null
                && getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
            return;
        }

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

                SharedPreferences myPrefs = getContext().getSharedPreferences("camera_image", 0);
                SharedPreferences.Editor edit = myPrefs.edit();
                edit.putString("url", mCropImageUri.toString());
                edit.apply();

                i.putExtra(MediaStore.EXTRA_OUTPUT, mCropImageUri);
                activity.startActivityForResult(i, CAMERA_CODE);
            }
        }

    }

    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpeg", storageDir);
    }

    private File createCameraFile(Context context, boolean temp) {
        if (getContext() == null) return null;

        File mediaStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (mediaStorageDir != null && !mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.i("mediaStorageDir", "failed to create directory");
                return null;
            }
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHss", Locale.getDefault()).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + (temp ? "TEMP_" : "IMG") + timestamp + ".jpg");
    }

    public Uri saveCameraFile(Context context, byte[] bytes) {
        File pictureFile = createCameraFile(context, true);

        if (pictureFile == null) {
            Log.i("pictureFile", "Error creating media file, check permissions");
            return null;
        }
        File outputMediaFile = null;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);
            Bitmap realImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            ExifInterface exifInterface = new ExifInterface(pictureFile.toString());

            if (exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")) {
                realImage = rotate(realImage, 90);
            } else if (exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")) {
                realImage = rotate(realImage, 270);
            } else if (exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")) {
                realImage = rotate(realImage, 180);
            } else if (exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")) {
                realImage = rotate(realImage, 90);
            }

            realImage.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            fileOutputStream.close();

            Matrix matrix = new Matrix();
            outputMediaFile = createCameraFile(context, false);

            if (outputMediaFile == null) {
                Log.i("Teste", "Failed create image");
                return null;
            }

            //Width nas duas posi????es para fazer o formato quadrado da foto
            Bitmap result = Bitmap.createBitmap(realImage, 0, 0, realImage.getWidth(), realImage.getWidth(), matrix, true);


            fileOutputStream = new FileOutputStream(outputMediaFile);

            result.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(outputMediaFile);
    }

    public interface OnImageCropped {
        void onImageCropped(Uri uri);

        void onImagePicked(Uri mCropImageUri);
    }

}
