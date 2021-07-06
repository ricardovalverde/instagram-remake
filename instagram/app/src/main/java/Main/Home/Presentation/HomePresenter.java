package Main.Home.Presentation;

import java.util.List;

import Main.Home.DataSource.HomeDataSource;
import Main.Presentation.MainView;
import common.model.Feed;
import common.presenter.Presenter;

public class HomePresenter implements Presenter<List<Feed>> {

    private final HomeDataSource homeDataSource;
    private MainView.HomeView view;

    public HomePresenter(HomeDataSource homeDataSource) {
        this.homeDataSource = homeDataSource;
    }

    public void setView(MainView.HomeView view) {
        this.view = view;
    }

    public void findFeed() {
        view.showProgressBar();
        homeDataSource.findFeed(this);
    }

    @Override
    public void onSuccess(List<Feed> response) {
        view.showFeed(response);
    }

    @Override
    public void onError(String messageError) {

    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
