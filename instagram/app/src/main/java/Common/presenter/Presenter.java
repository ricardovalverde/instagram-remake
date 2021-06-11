package Common.presenter;

import Common.model.UserAuth;

public interface Presenter {
    void onSuccess(UserAuth response);

    void onError(String messageError);

    void onComplete();
}
