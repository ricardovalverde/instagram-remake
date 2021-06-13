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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setStatusBarDark();
    }

    @Override
    protected void onInject() {
        RegisterEmailFragment fragment = new RegisterEmailFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.register_fragment,fragment,"fragment1");

        transaction.commit();


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    public static void launch(Context context){
        Intent intent = new Intent(context,RegisterActivity.class);
        context.startActivity(intent);
    }

}