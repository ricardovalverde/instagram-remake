package Main.Presentation;

import java.util.List;

import common.model.Feed;
import common.model.Post;
import common.model.User;
import common.view.View;

public interface MainView extends View {
    void scrollToolbarEnabled(boolean enabled);

    void showProfile(String user);

    void disposeProfileDetail();

    public interface ProfileView extends View {
        void showPhoto(String url);

        void showData(String name, String following, String followers, String nPosts, boolean editProfile, boolean follow);

        void showPosts(List<Post> posts);
    }

    public interface HomeView extends View {
        void showFeed(List<Feed> response);
    }

    public interface SearchView {
        void showUsers(List<User> users);
    }
}
