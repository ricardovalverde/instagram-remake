package common.model;

import android.graphics.drawable.Drawable;

public class Feed extends Post {
    private User publisher;

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }
}
