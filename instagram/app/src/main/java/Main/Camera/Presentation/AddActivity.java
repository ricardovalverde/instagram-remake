package Main.Camera.Presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.instagram.R;
import com.google.android.material.tabs.TabLayout;

import Main.Camera.DataSource.GalleryDataSource;
import Main.Camera.DataSource.GalleryLocalDataSource;
import butterknife.BindView;
import common.view.AbstractActivity;

public class AddActivity extends AbstractActivity implements AddView {

    @BindView(R.id.add_view_pager)
    ViewPager viewPager;

    @BindView(R.id.add_tab_layout)
    TabLayout tabLayout;

    public static void launch(Context context) {
        Intent intent = new Intent(context, AddActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            Drawable drawable = findDrawable(R.drawable.ic_close);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getWindow().setStatusBarColor(findColor(R.color.colorPrimaryDark));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                super.onTabSelected(tab);
                viewPager.setCurrentItem(tab.getPosition());
                //Log.i("Teste", " "+tab.getPosition());
            }
        });
    }

    @Override
    protected void onInject() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        GalleryDataSource dataSource = new GalleryLocalDataSource();
        GalleryPresenter presenter = new GalleryPresenter(dataSource);

        GalleryFragment galleryFragment = GalleryFragment.newInstance(this,presenter);
        CameraFragment cameraFragment = CameraFragment.newInstance(this);

        adapter.addFragment(galleryFragment);
        adapter.addFragment(cameraFragment);
        adapter.notifyDataSetChanged();

        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tabLeft = tabLayout.getTabAt(0);
        TabLayout.Tab tabRigth = tabLayout.getTabAt(1);


        if (tabLeft != null) {
            tabLeft.setText(getString(R.string.gallery));
        }

        if (tabRigth != null) {
            tabRigth.setText(getString(R.string.photo));
        }
        viewPager.setCurrentItem(adapter.getCount() - 1);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add;
    }

    @Override
    public void onImageLoaded(Uri uri) {
        AddCaptionActivity.launch(this, uri);
        finish();
    }
}