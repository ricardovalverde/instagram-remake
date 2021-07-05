package Main.Profile.Presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;

import Main.Presentation.MainView;
import butterknife.BindView;
import common.view.AbstractFragment;


public class ProfileFragment extends AbstractFragment<ProfilePresenter> {

    private MainView mainView;

    @BindView(R.id.profile_recycler_view)
    RecyclerView recyclerView;

    public static ProfileFragment newInstance(MainView mainView) {
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setMainView(mainView);
        return profileFragment;
    }

    private void setMainView(MainView mainView) {
        this.mainView = mainView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new adapter());

        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private static class viewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image_grid);
        }

        public void bind(int image) {
            this.imageView.setImageResource(image);

        }
    }

    private class adapter extends RecyclerView.Adapter<viewHolder> {

        private final int[] imagens = new int[]{
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,
                R.drawable.ic_insta_add

        };

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new viewHolder(getLayoutInflater().inflate(R.layout.item_profile_grid, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileFragment.viewHolder holder, int position) {
            holder.bind(imagens[position]);

        }

        @Override
        public int getItemCount() {
            return imagens.length;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_profile;
    }


}
