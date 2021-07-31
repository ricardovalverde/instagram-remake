package Main.Camera.DataSource;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.List;

import common.presenter.Presenter;

public class GalleryLocalDataSource implements GalleryDataSource {
    @Override
    public void findPictures(Context context, Presenter presenter) {
        List<String> images = null;

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_ADDED
        };
        if (context != null && context.getContentResolver() != null) {
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, MediaStore.Images.Media.DATE_ADDED);
            if (cursor != null) {

                int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                int columnIndexFolder = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                int columnIndexDataAdded = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);

                while (cursor.moveToNext()) {
                    String absolutePath = cursor.getString(columnIndexData);
                    String folder = cursor.getString(columnIndexFolder);
                    String dateAdded = cursor.getString(columnIndexDataAdded);

                    images.add(absolutePath);
                }
            }
        }
        if (images != null && !images.isEmpty()) {
            presenter.onSuccess(images);
        }
    }
}
