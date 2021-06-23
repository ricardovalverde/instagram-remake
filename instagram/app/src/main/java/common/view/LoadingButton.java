package common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.instagram.R;

public class LoadingButton extends FrameLayout {

    private AppCompatButton button_enter;
    private ProgressBar progressBar;
    private String text;

    public LoadingButton(@NonNull Context context) {
        super(context);
        setup(context, null);
    }

    public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.button_loading, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestButton, 0, 0);
        text = typedArray.getString(R.styleable.TestButton_text);
        typedArray.recycle();

        button_enter = (AppCompatButton) getChildAt(0);
        button_enter.setText(text);
        button_enter.setEnabled(false);

        progressBar = (ProgressBar) getChildAt(1);
        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
        progressBar.getIndeterminateDrawable().setColorFilter(colorFilter);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        button_enter.setOnClickListener(l);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        button_enter.setEnabled(enabled);
    }

    public void showProgressBar(boolean enabled) {
        progressBar.setVisibility(enabled ? VISIBLE : GONE);
        button_enter.setText(enabled ? "" : text);
    }
}
