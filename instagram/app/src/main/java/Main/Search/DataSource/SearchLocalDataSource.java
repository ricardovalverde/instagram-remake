package Main.Search.DataSource;

import java.util.List;

import common.model.Database;
import common.model.User;
import common.presenter.Presenter;

public class SearchLocalDataSource implements SearchDataSource {
    @Override
    public void findUsers(String query, Presenter<List<User>> presenter) {
        Database database = Database.getINSTANCE();
        database.findUsers(database.getUser().getUserId(), query)
                .addOnSuccessListener(presenter::onSuccess)
                .addOnFailure(e -> {
                    presenter.onError(e.getMessage());
                })
                .addOnComplete(presenter::onComplete);
    }
}
