package Login.DataSource;

import common.model.Database;
import common.model.UserAuth;
import common.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {
    @Override
    public void login(String email, String password, Presenter presenter) {

        Database.getINSTANCE().login(email, password)

                .addOnSuccessListener(new Database.OnSuccessListener<UserAuth>() {
                    @Override
                    public void onSuccess(UserAuth response) {
                        presenter.onSuccess(response);
                    }
                })
                .addOnFailure(new Database.OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        presenter.onError(e.getMessage());
                    }
                })
                .addOnComplete(new Database.OnCompleteListener() {
                    @Override
                    public void onComplete() {
                        presenter.onComplete();
                    }
                });
    }
}
