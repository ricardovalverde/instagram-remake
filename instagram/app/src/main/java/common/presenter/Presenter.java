package common.presenter;

import common.model.UserAuth;

public interface Presenter {
    void onSuccess(UserAuth response);

    void onError(String messageError);

    void onComplete();
}
