package Main.Profile.DataSource;

import java.util.List;

import common.model.Database;
import common.model.Post;
import common.model.User;
import common.model.UserProfile;
import common.presenter.Presenter;

public class ProfileLocalDataSource implements ProfileDataSource {
    @Override
    public void findUser(String uuid, Presenter<UserProfile> profilePresenter) {
        Database dataBase = Database.getINSTANCE();
        dataBase.findUser(uuid)
                .addOnSuccessListener((Database.OnSuccessListener<User>) user1 -> {
                    dataBase.findPosts(uuid)
                            .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
                                dataBase.following(dataBase.getUser().getUserId(), uuid)
                                        .addOnSuccessListener((Database.OnSuccessListener<Boolean>) following -> {
                                            profilePresenter.onSuccess(new UserProfile(user1, posts, following));
                                            profilePresenter.onComplete();
                                        });
                            });
                });
    }

    @Override
    public void follow(String user) {
        Database.getINSTANCE().follow(Database.getINSTANCE().getUser().getUserId(), user);

    }

    @Override
    public void unfollow(String user) {
        Database.getINSTANCE().unfollow(Database.getINSTANCE().getUser().getUserId(), user);
    }
}
