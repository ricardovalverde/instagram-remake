package Common.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import Common.util.Colors;
import Common.util.Drawables;
import butterknife.ButterKnife;

public abstract class AbstractFragment extends Fragment implements View {

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(getLayout(), container,false);
        ButterKnife.bind(this, (android.view.View) view);
        
        return (android.view.View) view;
        
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setStatusBarDark() {

    }

    public Drawable findDrawble(@DrawableRes int drawbleId) {

        return Drawables.getDrawable(getContext(), drawbleId);
    }

    public int findColor(@ColorRes int colorId) {
        return Colors.getColor(getContext(), colorId);
    }

    protected abstract @LayoutRes
    int getLayout();

}
