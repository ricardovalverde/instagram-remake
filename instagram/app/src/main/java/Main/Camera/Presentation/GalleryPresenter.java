package Main.Camera.Presentation;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import Main.Camera.DataSource.GalleryDataSource;
import common.presenter.Presenter;

public class GalleryPresenter implements Presenter<List<String>> {
    private final GalleryDataSource dataSource;
    private GalleryView view;

    public GalleryPresenter(GalleryDataSource dataSource) {

        this.dataSource = dataSource;
    }

    public void setView(GalleryView view) {
        this.view = view;
    }

    public void findPictures(Context context) {
        view.showProgressBar();
        dataSource.findPictures(context, this);
    }

    @Override
    public void onSuccess(List<String> response) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (String res : response) {
            Uri uri = Uri.parse(res);
            uriList.add(uri);
        }
        view.onPictureLoaded(uriList);

    }

    @Override
    public void onError(String messageError) {

    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
