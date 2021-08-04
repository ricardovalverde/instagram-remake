package Main.Search.DataSource;

import java.util.List;

import common.model.User;
import common.presenter.Presenter;

public interface SearchDataSource {
    void findUsers(String query, Presenter<List<User>> presenter);
}
