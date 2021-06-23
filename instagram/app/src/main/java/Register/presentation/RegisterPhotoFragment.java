package Register.presentation;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.instagram.R;

import common.view.CustomDialog;

public class RegisterPhotoFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_register_photo, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle(R.string.define_photo_profile)
                .addButton((v) -> {
                    switch (v.getId()) {
                        case R.string.take_picture:
                            Log.i("Teste", "take pic");
                            break;
                        case R.string.search_gallery:
                            Log.i("Teste", "search gal");
                            break;
                    }


                }, R.string.take_picture, R.string.search_gallery)
                .build();
        customDialog.show();


    }
}
