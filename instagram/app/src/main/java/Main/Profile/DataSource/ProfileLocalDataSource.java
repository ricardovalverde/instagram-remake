package Main.Profile.DataSource;

import java.util.List;

import common.model.Database;
import common.model.Post;
import common.model.User;
import common.model.UserProfile;
import common.presenter.Presenter;

public class ProfileLocalDataSource implements ProfileDataSource {
    @Override
    public void findUser(String user, Presenter<UserProfile> profilePresenter) {
        Database dataBase = Database.getINSTANCE();
        dataBase.findUser(user)
                .addOnSuccessListener((Database.OnSuccessListener<User>) user1 -> {
                    dataBase.findPosts(user1.getUuid())
                            .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
                                profilePresenter.onSuccess(new UserProfile(user1, posts));
                                profilePresenter.onComplete();
                            });
                });
    }
}
