package Main.Camera.DataSource;

import android.content.Context;

import common.presenter.Presenter;

public interface GalleryDataSource  {
    void findPictures(Context context, Presenter presenter);
}
