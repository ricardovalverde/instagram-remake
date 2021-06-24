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

import Main.MainActivity;
import butterknife.BindView;
import butterknife.OnClick;
import common.view.AbstractFragment;
import common.view.CustomDialog;
import common.view.LoadingButton;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {
    @BindView(R.id.register_button_next)
    LoadingButton buttonNext;

    public static RegisterPhotoFragment newInstance(RegisterPresenter registerPresenter) {
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();
        fragment.setPresenter(registerPresenter);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*CustomDialog customDialog = new CustomDialog.Builder(getContext())
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
        customDialog.show();*/
        buttonNext.setEnabled(true);
    }
    @OnClick(R.id.register_button_next)
    public void onButtonNextClick(){
        // TODO: 23/06/2021
    }

    @OnClick(R.id.register_button_jump)
    public void onButtonJumpClick(){
        presenter.jumpRegistration();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_photo;
    }
}
