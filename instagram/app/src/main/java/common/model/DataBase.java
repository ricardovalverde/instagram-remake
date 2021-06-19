package common.model;

import android.os.Handler;

import java.util.HashSet;
import java.util.Set;

public class DataBase {

    private static DataBase INSTANCE;
    private static Set<UserAuth> usersAuth;

    static {
        usersAuth = new HashSet<>();

        usersAuth.add(new UserAuth("user1@gmail.com", "1234"));
        usersAuth.add(new UserAuth("user2@gmail.com", "4132"));
        usersAuth.add(new UserAuth("user3@gmail.com", "5678"));
        usersAuth.add(new UserAuth("user4@gmail.com", "8567"));
    }

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;
    private UserAuth user;

    public static DataBase getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new DataBase();
        }
        return INSTANCE;
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

    public DataBase login(String email, String password) {
        timeOut(() -> {
            UserAuth user = new UserAuth();
            user.setEmail(email);
            user.setPassword(password);

            if (usersAuth.contains(user)) {
                this.user = user;
                onSuccessListener.onSuccess(user);
            } else {
                this.user = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado"));
            }
            onCompleteListener.onComplete();
        });
        return this;
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
