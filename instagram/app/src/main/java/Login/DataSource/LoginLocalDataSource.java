package Login.DataSource;

import common.model.DataBase;
import common.model.UserAuth;
import common.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {
    @Override
    public void login(String email, String password, Presenter presenter) {

        DataBase.getINSTANCE().login(email, password)

                .addOnSuccessListener(new DataBase.OnSuccessListener<UserAuth>() {
                    @Override
                    public void onSuccess(UserAuth response) {
                        presenter.onSuccess(response);
                    }
                })
                .addOnFailure(new DataBase.OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        presenter.onError(e.getMessage());
                    }
                })
                .addOnComplete(new DataBase.OnCompleteListener() {
                    @Override
                    public void onComplete() {
                        presenter.onComplete();
                    }
                });
    }
}
