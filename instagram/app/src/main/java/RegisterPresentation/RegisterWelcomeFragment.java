package RegisterPresentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.instagram.R;

public class RegisterWelcomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_register_welcome, container, false);
        // TODO: 22/05/2021 scroll gravity TOP
    }
}
