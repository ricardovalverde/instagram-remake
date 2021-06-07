package Common.presenter;

public interface Presenter {
    void onSuccess();
    void onError(String messageError);
    void onComplete();
}
