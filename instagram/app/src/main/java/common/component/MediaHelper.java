package common.component;

import android.app.Activity;

import androidx.fragment.app.Fragment;


public class MediaHelper {
    private static MediaHelper INSTANCE;
    private Activity activity;
    private Fragment fragment;

    public static MediaHelper getINSTANCE(Activity activity) {
        if (INSTANCE == null) {
            INSTANCE = new MediaHelper();
            INSTANCE.setActivity(activity);

        }
        return INSTANCE;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    public static MediaHelper getINSTANCE(Fragment fragment) {
        if (INSTANCE == null) {
            INSTANCE = new MediaHelper();
            INSTANCE.setFragment(fragment);
        }
        return INSTANCE;
    }

    private void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

}
