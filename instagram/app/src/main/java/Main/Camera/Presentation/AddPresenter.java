package Main.Camera.Presentation;

import android.net.Uri;

import Main.Camera.DataSource.AddDataSource;
import common.presenter.Presenter;

public class AddPresenter implements Presenter<Void> {


    private final AddCaptionView view;
    private final AddDataSource dataSource;

    public AddPresenter(AddCaptionView view, AddDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    public void createPost(Uri uri, String caption) {
        view.showProgressBar();
        dataSource.savePost(uri, caption, this);
    }

    @Override
    public void onSuccess(Void response) {
        view.postSaved();
    }

    @Override
    public void onError(String messageError) {

    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
