package Main.Camera.Presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.instagram.R;

import Main.Camera.DataSource.AddLocalDataSource;
import butterknife.BindView;
import common.view.AbstractActivity;

public class AddCaptionActivity extends AbstractActivity implements AddCaptionView {
    @BindView(R.id.main_add_caption_image_view)
    ImageView previewImageView;

    @BindView(R.id.main_add_caption_edit_text)
    EditText textCaption;

    private Uri uri;
    private AddPresenter presenter;


    public static void launch(Context context, Uri uri) {
        Intent intent = new Intent(context, AddCaptionActivity.class);
        intent.putExtra("uri", uri);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.add_caption_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            Drawable drawable = findDrawable(R.drawable.ic_arrow_back);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void onInject() {
        uri = getIntent().getExtras().getParcelable("uri");
        previewImageView.setImageURI(uri);

        AddLocalDataSource dataSource = new AddLocalDataSource();
        presenter = new AddPresenter(this,dataSource);
    }

    @Override
    public void postSaved() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                presenter.createPost(uri, textCaption.getText().toString());
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_caption;
    }
}