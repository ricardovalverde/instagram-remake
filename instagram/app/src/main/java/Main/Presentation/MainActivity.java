package Main.Presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.instagram.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import Main.Camera.Presentation.AddActivity;
import Main.Home.DataSource.HomeDataSource;
import Main.Home.DataSource.HomeLocalDataSource;
import Main.Home.Presentation.HomeFragment;
import Main.Home.Presentation.HomePresenter;
import Main.Profile.DataSource.ProfileDataSource;
import Main.Profile.DataSource.ProfileLocalDataSource;
import Main.Profile.Presentation.ProfileFragment;
import Main.Profile.Presentation.ProfilePresenter;
import Main.SearchPresentation.SearchFragment;
import common.view.AbstractActivity;

public class MainActivity extends AbstractActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainView {


    public static final String ACT_SOURCE = "act_source";
    public static final int LOGIN_ACTIVITY = 0;
    public static final int REGISTER_ACTIVITY = 1;

    private ProfilePresenter profilePresenter;
    private HomePresenter homePresenter;

    Fragment homeFragment;
    Fragment profileFragment;
    //Fragment cameraFragment;
    Fragment searchFragment;
    Fragment active;


    public static void launch(Context context, int source) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ACT_SOURCE, source);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Tirar LoginActivity da pilha
        context.startActivity(intent);
    }

    @Override
    protected void onInject() {
        ProfileDataSource profileDataSource = new ProfileLocalDataSource();
        profilePresenter = new ProfilePresenter(profileDataSource);

        HomeDataSource homeDataSource = new HomeLocalDataSource();
        homePresenter = new HomePresenter(homeDataSource);

        homeFragment = HomeFragment.newInstance(this, homePresenter);
        profileFragment = ProfileFragment.newInstance(this, profilePresenter);

        searchFragment = new SearchFragment();
        //cameraFragment = new CameraFragment();

        active = homeFragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_fragment, profileFragment).hide(profileFragment).commit();
        //fragmentManager.beginTransaction().add(R.id.main_fragment, cameraFragment).hide(cameraFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_fragment, searchFragment).hide(searchFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_fragment, homeFragment).hide(homeFragment).commit();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            Drawable icon_camera = findDrawable(R.drawable.ic_insta_camera);
            getSupportActionBar().setHomeAsUpIndicator(icon_camera);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int source = extras.getInt(ACT_SOURCE);
            if (source == REGISTER_ACTIVITY) {

                getSupportFragmentManager().beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                scrollToolbarEnabled(true);
                profilePresenter.findUser();
            }
        }

    }


    @Override
    public void scrollToolbarEnabled(boolean enabled) {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.main_appbar);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();

        if (enabled) {
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        } else {
            params.setScrollFlags(0);
            appBarLayoutParams.setBehavior(null);
        }
        appBarLayout.setLayoutParams(appBarLayoutParams);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.menu_bottom_home:
                fragmentManager.beginTransaction().hide(active).show(homeFragment).commit();
                scrollToolbarEnabled(false);
  //              homePresenter.findFeed();
                active = homeFragment;
                return true;
            case R.id.menu_bottom_search:
                fragmentManager.beginTransaction().hide(active).show(searchFragment).commit();
                active = searchFragment;
                return true;
            case R.id.menu_bottom_add:
                /*fragmentManager.beginTransaction().hide(active).show(cameraFragment).commit();
                active = cameraFragment;*/
                AddActivity.launch(this);
                return true;
            case R.id.menu_bottom_profile:
                fragmentManager.beginTransaction().hide(active).show(profileFragment).commit();
                scrollToolbarEnabled(true);
      //          profilePresenter.findUser();
                active = profileFragment;
                return true;
        }
        return false;
    }

    @Override
    public void showProgressBar() {
        ProgressBar progressBar = findViewById(R.id.main_progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {
        ProgressBar progressBar = findViewById(R.id.main_progress);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


}