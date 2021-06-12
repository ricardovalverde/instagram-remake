package RegisterPresentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.instagram.R;

import Common.view.AbstractFragment;

public class RegisterEmailFragment extends AbstractFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_register_email, container, false);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_email;
    }
}
