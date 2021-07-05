package common.model;

import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DataBase {

    private static DataBase INSTANCE;
    private UserAuth userAuth;

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;

    private static Set<UserAuth> usersAuth;
    private static Set<User> users;
    private static Set<Uri> storages;
    private static HashMap<String, HashSet<Post>> posts;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();
        storages = new HashSet<>();
        posts = new HashMap<>();

        usersAuth.add(new UserAuth("user1@gmail.com", "1234"));
        usersAuth.add(new UserAuth("user2@gmail.com", "4132"));
        usersAuth.add(new UserAuth("user3@gmail.com", "5678"));
        usersAuth.add(new UserAuth("user4@gmail.com", "8567"));
    }


    public static DataBase getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new DataBase();
            INSTANCE.init();
        }
        return INSTANCE;
    }

    public void init() {
        String email = "user1@gmail.com";
        String password = "123";
        String name = "user1";

        UserAuth userAuth = new UserAuth();
        userAuth.setPassword(password);
        userAuth.setEmail(email);

        usersAuth.add(userAuth);

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setUuid(userAuth.getUserId());

        users.add(user);
        this.userAuth = userAuth;
    }

    public <T> DataBase addOnSuccessListener(OnSuccessListener<T> successListener) {
        this.onSuccessListener = successListener;
        return this;
    }

    public DataBase addOnFailure(OnFailureListener failureListener) {
        this.onFailureListener = failureListener;
        return this;
    }

    public DataBase addOnComplete(OnCompleteListener completeListener) {
        this.onCompleteListener = completeListener;
        return this;
    }

    public DataBase findPosts(String uuid) {
        timeOut(() -> {
            HashMap<String, HashSet<Post>> posts = DataBase.posts;
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

    public DataBase findUser(String uuid) {
        timeOut(() -> {

            Set<User> users = DataBase.users;
            User response = null;

            for (User user : users) {
                if (user.getUuid().equals(uuid)) {
                    response = user;
                    break;
                }
            }

            if (onSuccessListener != null && response != null) {
                onSuccessListener.onSuccess(response);
            }
            else if (onFailureListener != null) {
                onFailureListener.onFailure(new IllegalAccessException("Usuário não encontrado"));
            }

            if (onCompleteListener != null) {
                onCompleteListener.onComplete();
            }
        });
        return this;
    }


    public DataBase addPhoto(String uuid, Uri uri) {
        timeOut(() -> {
            Set<User> users = DataBase.users;
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

    public DataBase createUser(String name, String email, String password) {
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
                this.userAuth = userAuth;
                if (onSuccessListener != null)
                    onSuccessListener.onSuccess(userAuth);
            } else {
                this.userAuth = null;
                if (onFailureListener != null)
                    onFailureListener.onFailure(new IllegalAccessException("Usuário já existe"));
            }
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    public DataBase login(String email, String password) {
        timeOut(() -> {
            UserAuth user = new UserAuth();
            user.setEmail(email);
            user.setPassword(password);

            if (usersAuth.contains(user)) {
                this.userAuth = user;
                onSuccessListener.onSuccess(user);
            } else {
                this.userAuth = null;
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
        new Handler().postDelayed(r, 2000);
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
