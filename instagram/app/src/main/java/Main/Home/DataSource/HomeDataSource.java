package Main.Home.DataSource;

import java.util.List;

import common.model.Feed;
import common.presenter.Presenter;

public interface HomeDataSource {
    void findFeed(Presenter<List<Feed>> presenter);

}
