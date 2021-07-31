package Main.Camera.Presentation;

import android.net.Uri;

import java.util.List;

import common.view.View;

public interface GalleryView extends View {
    void onPictureLoaded(List<Uri> uriList);
}
