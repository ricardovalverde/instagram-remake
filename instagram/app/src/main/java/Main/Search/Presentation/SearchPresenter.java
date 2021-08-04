package Main.Search.Presentation;

import java.util.List;

import Main.Presentation.MainView;
import Main.Search.DataSource.SearchDataSource;
import common.model.User;
import common.presenter.Presenter;

public class SearchPresenter implements Presenter<List<User>> {

    private final SearchDataSource dataSource;
    private MainView.SearchView view;

    public SearchPresenter(SearchDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.SearchView view) {
        this.view = view;
    }

    public void findUsers(String newText) {
        dataSource.findUsers(newText, this);
    }

    @Override
    public void onSuccess(List<User> response) {
        view.showUsers(response);
    }

    @Override
    public void onError(String messageError) {

    }

    @Override
    public void onComplete() {

    }


}
