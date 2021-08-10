package common.model;

import android.net.Uri;

import java.util.Objects;

public class Post {

    private String uuid;
    private String caption;
    private String photoUrl;
    private long timestamp;
    private Uri uri;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return timestamp == post.timestamp &&
                Objects.equals(caption, post.caption) &&
                Objects.equals(uri, post.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caption, timestamp, uri);
    }
}
