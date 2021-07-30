package Main.Camera.DataSource;

import android.net.Uri;

import common.model.Database;
import common.presenter.Presenter;

public class AddLocalDataSource implements AddDataSource{
    @Override
    public void savePost(Uri uri, String caption, Presenter presenter) {
        Database db = Database.getINSTANCE();
        db.createPost(db.getUser().getUserId(), uri, caption)
                .addOnSuccessListener((Database.OnSuccessListener<Void>) presenter ::onSuccess)
                .addOnFailure(e -> presenter.onError(e.getMessage()))
                .addOnComplete(presenter :: onComplete);
    }
}
