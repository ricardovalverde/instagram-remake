package Main.Camera.Presentation;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;

import java.util.List;

import butterknife.BindView;
import common.view.AbstractFragment;

public class GalleryFragment extends AbstractFragment<GalleryPresenter> implements GalleryView {
    @BindView(R.id.main_gallery_scroll_view)
    NestedScrollView scrollView;

    @BindView(R.id.main_gallery_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.main_gallery_top)
    ImageView imageView;


    private AddView addView;

    public static GalleryFragment newInstance(AddView view, GalleryPresenter presenter) {
        GalleryFragment galleryFragment = new GalleryFragment();
        presenter.setView(galleryFragment);

        galleryFragment.setPresenter(presenter);
        galleryFragment.addView(view);


        return galleryFragment;
    }

    private void addView(AddView view) {
        this.addView = view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // TODO: 31/07/2021
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_gallery;
    }

    @Override
    public void onPictureLoaded(List<Uri> uriList) {

    }
}
