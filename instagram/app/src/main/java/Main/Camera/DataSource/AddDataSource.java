package Main.Camera.DataSource;

import android.net.Uri;

import common.presenter.Presenter;

public interface AddDataSource {
    void savePost(Uri uri,String caption, Presenter presenter);
}
