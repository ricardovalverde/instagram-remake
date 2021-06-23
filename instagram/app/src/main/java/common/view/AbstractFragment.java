package common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import common.util.Colors;
import common.util.Drawables;
import common.util.Keyboards;

public abstract class AbstractFragment<P> extends Fragment implements MainView {

    protected P presenter;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void setStatusBarDark() {

    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public Drawable findDrawable(@DrawableRes int drawableId) {
        return Drawables.getDrawable(getContext(), drawableId);
    }

    public InputMethodManager hideInput(Context context, EditText editText) {
        return Keyboards.hideInput(context, editText);
    }


    public int findColor(@ColorRes int colorId) {
        return Colors.getColor(getContext(), colorId);
    }

    protected abstract @LayoutRes
    int getLayout();
}
