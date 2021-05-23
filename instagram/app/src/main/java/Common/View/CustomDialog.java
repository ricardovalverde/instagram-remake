package Common.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.instagram.R;

import org.w3c.dom.Text;

public class CustomDialog extends Dialog {
    private TextView titleView;
    private TextView[] textViews;
    private LinearLayout dialog_container;
    private LinearLayout.LayoutParams layoutParams;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog_container = findViewById(R.id.dialog_container);
        layoutParams.setMargins(30,50,30,50);






    }
}
