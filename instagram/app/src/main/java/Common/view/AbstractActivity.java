package Common.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import Common.util.Drawables;
import butterknife.ButterKnife;

public abstract class AbstractActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    protected abstract @LayoutRes
    int getLayout();

    public Drawable findDrawable(@DrawableRes int drawableId) {
        return Drawables.getDrawable(this, drawableId);
    }

    public void showProgress() {

    }

    public void hideProgress() {

    }


}
