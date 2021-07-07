package Register.datasource;

import android.net.Uri;

import common.model.Database;
import common.model.UserAuth;
import common.presenter.Presenter;

public class RegisterLocalDataSource implements RegisterDataSource {
    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        Database.getINSTANCE().createUser(name, email, password)
                .addOnSuccessListener((Database.OnSuccessListener<UserAuth>) presenter::onSuccess)
                .addOnFailure(e -> presenter.onError(e.getMessage()))
                .addOnComplete(presenter::onComplete);
    }

    @Override
    public void addPhoto(Uri uri, Presenter presenter) {
        Database db = Database.getINSTANCE();
        db.addPhoto(db.getUser().getUserId(), uri)
                .addOnSuccessListener((Database.OnSuccessListener<Boolean>) presenter::onSuccess);
    }
}
