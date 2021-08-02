package Main.Camera.Presentation;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;

import java.util.ArrayList;
import java.util.List;

import Main.Profile.Presentation.ProfileFragment;
import butterknife.BindView;
import common.view.AbstractFragment;

public class GalleryFragment extends AbstractFragment<GalleryPresenter> implements GalleryView {
    @BindView(R.id.main_gallery_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.main_gallery_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.main_gallery_top)
    ImageView imageView;


    private AddView addView;
    private GalleryAdapter galleryAdapter;
    private Uri uri;

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

        galleryAdapter = new GalleryAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(galleryAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.findPictures(getContext());
    }

    @Override
    public void onPictureLoaded(List<Uri> uriList) {
        if (uriList.size() > 0) {
            imageView.setImageURI(uriList.get(0));
            this.uri = uriList.get(0);
        }
        galleryAdapter.setUri(uriList, uri1 -> {
            this.uri = uri1;
            imageView.setImageURI(uri1);
            nestedScrollView.smoothScrollTo(0,0);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gallery, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_go){
            addView.onImageLoaded(uri);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_gallery;
    }

    public class GalleryAdapter extends RecyclerView.Adapter<galleryHolder> {

        private List<Uri> uriList = new ArrayList<>();
        private GalleryItemClickListener listener;

        public void setUri(List<Uri> uris, GalleryItemClickListener listener) {
            this.uriList = uris;
            this.listener = listener;
        }

        @NonNull
        @Override
        public galleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_profile_grid, parent, false);
            return new galleryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull galleryHolder holder, int position) {
            holder.bind(uriList.get(position));
            holder.imageView.setOnClickListener(v -> {
                Uri uri = uriList.get(position);
                listener.onClick(uri);
            });
        }

        @Override
        public int getItemCount() {
            return uriList.size();
        }

    }

    public static class galleryHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;


        public galleryHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image_grid);
        }

         void bind(Uri uri) {
            this.imageView.setImageURI(uri);
        }
    }

    interface GalleryItemClickListener {
        void onClick(Uri uri);
    }

}
