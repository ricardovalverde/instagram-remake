package common.model;

import android.net.Uri;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Database INSTANCE;
    private static UserAuth userAuth;
    private static Set<UserAuth> usersAuth;
    private static Set<User> users;
    private static Set<Uri> storages;
    private static HashMap<String, HashSet<Post>> posts;
    private static HashMap<String, HashSet<Feed>> feed;
    private static HashMap<String, HashSet<String>> followers;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();
        storages = new HashSet<>();
        posts = new HashMap<>();
        feed = new HashMap<>();
        followers = new HashMap<>();

        String email = "user1@gmail.com";
        String password = "123";
        String name = "user";
        init(email, password, name);

        for (int i = 0; i < 31; i++) {
            email = "user" + i + "@gmail.com";
            password = "1232";
            name = "user" + i;
            init(email, password, name);
        }

    }

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;

    public static Database getINSTANCE() {
        return new Database();
    }


    public static void init(String email, String password, String name) {


        UserAuth userAuth = new UserAuth();
        userAuth.setPassword(password);
        userAuth.setEmail(email);

        usersAuth.add(userAuth);

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setUuid(userAuth.getUserId());

        users.add(user);
        Database.userAuth = userAuth;
    }

    public Database findPosts(String uuid) {
        timeOut(() -> {
            HashMap<String, HashSet<Post>> posts = Database.posts;
            HashSet<Post> response = posts.get(uuid);

            if (response == null) {
                response = new HashSet<>();
            }
            if (onSuccessListener != null) {
                onSuccessListener.onSuccess(new ArrayList<>(response));
            }
            if (onCompleteListener != null) {
                onCompleteListener.onComplete();
            }
        });

        return this;
    }

    public Database findFeed(String uuid) {
        timeOut(() -> {
            HashMap<String, HashSet<Feed>> feedMap = Database.feed;
            HashSet<Feed> feeds = feedMap.get(uuid);

            if (feeds == null) {
                feeds = new HashSet<>();
            }
            if (onSuccessListener != null) {
                onSuccessListener.onSuccess(new ArrayList<>(feeds));
            }
            if (onCompleteListener != null) {
                onCompleteListener.onComplete();
            }

        });

        return this;
    }

    public Database findUser(String uuid) {
        timeOut(() -> {

            Set<User> users = Database.users;
            User response = null;

            for (User user : users) {
                if (user.getUuid().equals(uuid)) {
                    response = user;
                    break;
                }
            }

            if (onSuccessListener != null && response != null) {
                onSuccessListener.onSuccess(response);
            } else if (onFailureListener != null) {
                onFailureListener.onFailure(new IllegalAccessException("Usuário não encontrado"));
            }

            if (onCompleteListener != null) {
                onCompleteListener.onComplete();
            }
        });
        return this;
    }

    public Database findUsers(String uuid, String query) {
        timeOut(() -> {
            ArrayList<User> users = new ArrayList<>();

            for (User user : Database.users) {
                if (!user.getUuid().equals(uuid) && user.getName().contains(query)) {
                    users.add(user);
                }
            }
            onSuccessListener.onSuccess(users);
            onCompleteListener.onComplete();
        });
        return this;
    }

    public Database follow(String uuidMe, String uuid) {
        timeOut(() -> {
            HashMap<String, HashSet<String>> followersMap = Database.followers;
            HashSet<String> followers = followersMap.get(uuid);

            if (followers == null) {
                followers = new HashSet<>();
                followersMap.put(uuid, followers);
            }
            followers.add(uuidMe);

            if (onSuccessListener != null) {
                onSuccessListener.onSuccess(true);
            }
        });
        return this;
    }

    public Database unfollow(String uuidMe, String uuid) {
        timeOut(() -> {
            HashMap<String, HashSet<String>> followersMap = Database.followers;
            HashSet<String> followers = followersMap.get(uuid);

            if (followers != null) {
                followers = new HashSet<>();
                followersMap.put(uuid, followers);
            }
            followers.remove(uuidMe);
        });
        return this;
    }

    public Database following(String uuidMe, String uuid) {
        timeOut(() -> {
            HashMap<String, HashSet<String>> followers = Database.followers;
            HashSet<String> followerOfUser = followers.get(uuid);

            if (followerOfUser == null) {
                followerOfUser = new HashSet<>();
            }
            boolean following = false;

            for (String userUUID : followerOfUser) {
                if (userUUID.equals(uuidMe)) {
                    following = true;
                    break;
                }
            }
            if (onSuccessListener != null) {
                onSuccessListener.onSuccess(following);
            } else if (onFailureListener != null) {
                onFailureListener.onFailure(new IllegalAccessException("Usuário não encontrado"));
            }
            if (onCompleteListener != null) {
                onCompleteListener.onComplete();
            }
        });
        return this;
    }


    public Database addPhoto(String uuid, Uri uri) {
        timeOut(() -> {
            Set<User> users = Database.users;
            for (User user : users) {
                if (user.getUuid().equals(uuid)) {
                    user.setUri(uri);
                }
            }
            storages.add(uri);
            onSuccessListener.onSuccess(true);
        });
        return this;
    }

    public Database createUser(String name, String email, String password) {
        timeOut(() -> {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);

            usersAuth.add(userAuth);

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setUuid(userAuth.getUserId());

            boolean added = users.add(user);
            if (added) {
                Database.userAuth = userAuth;
                HashMap<String, HashSet<String>> followersMap = Database.followers;
                followersMap.put(userAuth.getUserId(), new HashSet<>());

                HashMap<String, HashSet<Feed>> feedMap = Database.feed;
                feedMap.put(userAuth.getUserId(), new HashSet<>());

                if (onSuccessListener != null)
                    onSuccessListener.onSuccess(userAuth);
            } else {
                Database.userAuth = null;
                if (onFailureListener != null)
                    onFailureListener.onFailure(new IllegalAccessException("Usuário já existe"));
            }
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    public Database createPost(String uuid, Uri uri, String caption) {
        timeOut(() -> {
            HashMap<String, HashSet<Post>> postMap = Database.posts;
            HashSet<Post> posts = postMap.get(uuid);

            if (posts == null) {
                posts = new HashSet<>();
                postMap.put(uuid, posts);
            }
            Post post = new Post();

            post.setUri(uri);
            post.setCaption(caption);
            post.setTimestamp(System.currentTimeMillis());
            post.setUuid(String.valueOf(post.hashCode()));

            posts.add(post);

            HashMap<String, HashSet<String>> followerMap = Database.followers;
            HashSet<String> followers = followerMap.get(uuid);

            if (followers == null) {
                followers = new HashSet<>();
                followerMap.put(uuid, followers);
            } else {
                HashMap<String, HashSet<Feed>> feedMap = Database.feed;
                for (String follower : followers) {
                    HashSet<Feed> feeds = feedMap.get(follower);


                    if (feeds != null) {
                        Feed feed = new Feed();
                        feed.setUri(post.getUri());
                        feed.setCaption(post.getCaption());
                        feed.setTimestamp(post.getTimestamp());

                        feeds.add(feed);
                    }
                }
                HashSet<Feed> feedMe = feedMap.get(uuid);

                if (feedMe != null) {
                    Feed feed = new Feed();
                    feed.setUri(post.getUri());
                    feed.setCaption(post.getCaption());
                    feed.setTimestamp(post.getTimestamp());

                    feedMe.add(feed);
                }
            }
            if (onSuccessListener != null) {
                onSuccessListener.onSuccess(null);
            }
            if (onCompleteListener != null) {
                onCompleteListener.onComplete();
            }

        });

        return this;
    }

    public Database login(String email, String password) {
        timeOut(() -> {
            UserAuth user = new UserAuth();
            user.setEmail(email);
            user.setPassword(password);

            if (usersAuth.contains(user)) {
                Database.userAuth = user;
                onSuccessListener.onSuccess(user);
            } else {
                Database.userAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado"));
            }
            onCompleteListener.onComplete();
        });
        return this;
    }

    public UserAuth getUser() {
        return userAuth;
    }

    private void timeOut(Runnable r) {
        new Handler().postDelayed(r, 1000);
    }


    public <T> Database addOnSuccessListener(OnSuccessListener<T> successListener) {
        this.onSuccessListener = successListener;
        return this;
    }

    public Database addOnFailure(OnFailureListener failureListener) {
        this.onFailureListener = failureListener;
        return this;
    }

    public Database addOnComplete(OnCompleteListener completeListener) {
        this.onCompleteListener = completeListener;
        return this;
    }


    public interface OnSuccessListener<T> {
        void onSuccess(T response);
    }

    public interface OnFailureListener<T> {
        void onFailure(Exception e);
    }

    public interface OnCompleteListener<T> {
        void onComplete();
    }
}
