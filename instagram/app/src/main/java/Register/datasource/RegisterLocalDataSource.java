package Register.datasource;

import common.model.DataBase;
import common.model.UserAuth;
import common.presenter.Presenter;

public class RegisterLocalDataSource implements RegisterDataSource {
    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        DataBase.getINSTANCE().createUser(name, email, password)
                .addOnSuccessListener((DataBase.OnSuccessListener<UserAuth>) presenter::onSuccess)
                .addOnFailure(e -> presenter.onError(e.getMessage()))
                .addOnComplete(presenter::onComplete);
    }
}
