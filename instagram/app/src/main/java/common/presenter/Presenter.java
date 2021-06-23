package common.presenter;

public interface Presenter<T> {
    void onSuccess(T response);

    void onError(String messageError);

    void onComplete();
}
