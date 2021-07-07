package common.model;

import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Database INSTANCE;
    private static UserAuth userAuth;

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;

    private static Set<UserAuth> usersAuth;
    private static Set<User> users;
    private static Set<Uri> storages;

    private static HashMap<String, HashSet<Post>> posts;
    private static HashMap<String, HashSet<Feed>> feed;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();
        storages = new HashSet<>();
        posts = new HashMap<>();
        feed = new HashMap<>();

        init();

    }


    public static Database getINSTANCE() {

        return new Database();
        //if (INSTANCE == null) {

        //          INSTANCE = new DataBase();
        //        INSTANCE.init();
        //       }
        //  return INSTANCE;
    }


    public static void init() {
        String email = "user1@gmail.com";
        String password = "123";
        String name = "Patricia";

        UserAuth userAuth = new UserAuth();
        userAuth.setPassword(password);
        Log.i("teste", "passous aqui");
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
            HashMap<String, HashSet<Feed>> feed = Database.feed;
            HashSet<Feed> response = feed.get(uuid);

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
