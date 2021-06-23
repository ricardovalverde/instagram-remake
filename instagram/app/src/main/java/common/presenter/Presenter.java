package common.presenter;

import common.model.UserAuth;

public interface Presenter<T> {
    void onSuccess(T response);

    void onError(String messageError);

    void onComplete();
}
