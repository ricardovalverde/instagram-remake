package Main.Profile.DataSource;

import java.util.List;

import Main.Profile.Presentation.ProfilePresenter;
import common.model.Database;
import common.model.Post;
import common.model.User;
import common.model.UserProfile;
import common.presenter.Presenter;

public class ProfileLocalDataSource implements ProfileDataSource {
    @Override
    public void findUser(Presenter<UserProfile> profilePresenter) {
        Database dataBase = Database.getINSTANCE();
        dataBase.findUser(dataBase.getUser().getUserId())
                .addOnSuccessListener((Database.OnSuccessListener<User>) user -> {
                    dataBase.findPosts(user.getUuid())
                            .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
                                profilePresenter.onSuccess(new UserProfile(user, posts));
                                profilePresenter.onComplete();
                            });
                });
    }
}
