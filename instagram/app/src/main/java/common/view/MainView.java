package common.view;

import android.content.Context;

public interface MainView {
    Context getContext();

    void showProgressBar();

    void hideProgressBar();

    void setStatusBarDark();
}
