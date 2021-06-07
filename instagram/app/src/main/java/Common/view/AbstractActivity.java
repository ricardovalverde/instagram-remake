package Common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import Common.util.Drawables;
import butterknife.ButterKnife;

public abstract class AbstractActivity extends AppCompatActivity implements View{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);

        onInject();
    }

    protected abstract void onInject();

    protected abstract @LayoutRes
    int getLayout();

    public Drawable findDrawable(@DrawableRes int drawableId) {
        return Drawables.getDrawable(this, drawableId);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }
}
