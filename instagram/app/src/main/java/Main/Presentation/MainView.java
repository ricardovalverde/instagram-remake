package Main.Presentation;

import android.net.Uri;

import java.util.List;

import common.model.Feed;
import common.model.Post;
import common.view.View;

public interface MainView extends View {
    void scrollToolbarEnabled(boolean enabled);

    public interface ProfileView extends View {
        void showPhoto(Uri uri);

        void showData(String name, String following, String followers, String nPosts);

        void showPosts(List<Post> posts);
    }

    public interface HomeView extends View {
        void showFeed(List<Feed> response);
    }
}
