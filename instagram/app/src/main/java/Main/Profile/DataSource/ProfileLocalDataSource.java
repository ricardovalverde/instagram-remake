package Main.Profile.DataSource;

import java.util.List;

import common.model.DataBase;
import common.model.Post;
import common.model.User;
import common.model.UserProfile;
import common.presenter.Presenter;

public class ProfileLocalDataSource implements ProfileDataSource {
    @Override
    public void findUser(Presenter<UserProfile> profilePresenter) {
        DataBase dataBase = DataBase.getINSTANCE();
        dataBase.findUser(dataBase.getUser().getUserId())
                .addOnSuccessListener((DataBase.OnSuccessListener<User>) user -> {
                    dataBase.findPosts(user.getUuid())
                            .addOnSuccessListener((DataBase.OnSuccessListener<List<Post>>) posts -> {
                                profilePresenter.onSuccess(new UserProfile(user, posts));
                                profilePresenter.onComplete();
                            });
                });
    }
}
