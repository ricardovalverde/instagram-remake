package common.view;

import android.content.Context;

public interface MainView {
    Context getContext();

    void showProgress();

    void hideProgress();

    void setStatusBarDark();

}
