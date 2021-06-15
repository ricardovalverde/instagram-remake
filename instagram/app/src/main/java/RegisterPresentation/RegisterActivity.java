package RegisterPresentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.instagram.R;

import Common.view.AbstractActivity;

public class RegisterActivity extends AbstractActivity implements RegisterView{

    private RegisterPresenter registerPresenter;

    public static void launch(Context context){
        Intent intent = new Intent(context,RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onInject() {
        registerPresenter = new RegisterPresenter();
        RegisterEmailFragment fragment = new RegisterEmailFragment().newInstance(registerPresenter);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.register_fragment,fragment,"fragment1");

        transaction.commit();


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }



}