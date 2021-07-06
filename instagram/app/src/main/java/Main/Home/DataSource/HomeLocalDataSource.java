package Main.Home.DataSource;

import java.util.List;

import common.model.DataBase;
import common.model.Feed;
import common.presenter.Presenter;

public class HomeLocalDataSource implements HomeDataSource {
    @Override
    public void findFeed(Presenter<List<Feed>> presenter) {
        DataBase dataBase = DataBase.getINSTANCE();
        dataBase.findFeed(dataBase.getUser().getUserId())
                .addOnSuccessListener(presenter::onSuccess)
                .addOnFailure(e -> presenter.onError(e.getMessage()))
                .addOnComplete(presenter::onComplete);

    }
}
