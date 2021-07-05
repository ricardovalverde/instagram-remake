package Main.Profile.DataSource;

import java.util.List;

import Main.Profile.Presentation.ProfilePresenter;
import common.model.DataBase;
import common.model.Post;
import common.model.User;

public class ProfileLocalDataSource implements ProfileDataSource {
    @Override
    public void findUser(ProfilePresenter profilePresenter) {
        DataBase dataBase = DataBase.getINSTANCE();
        dataBase.findUser(dataBase.getUser().getUserId())
                .addOnSuccessListener((DataBase.OnSuccessListener<User>)user ->{
                    dataBase.findPosts(user.getUuid())
                            .addOnSuccessListener((DataBase.OnSuccessListener<List<Post>>) posts ->{

                            });
                });
    }
}
