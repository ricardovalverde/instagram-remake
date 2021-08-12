package Main.Camera.DataSource;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

import common.model.Feed;
import common.model.Post;
import common.model.User;
import common.presenter.Presenter;

public class AddFirebaseDataSource implements AddDataSource {
    @Override
    public void savePost(Uri uri, String caption, Presenter presenter) {
        String host = uri.getHost();

        if (host == null) {
            uri = Uri.fromFile(new File(uri.toString()));
        }

        String uid = FirebaseAuth.getInstance().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageReference = storageReference
                .child("images/")
                .child(uid)
                .child(uri.getLastPathSegment());

        imageReference.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageReference.getDownloadUrl()
                            .addOnSuccessListener(uriResponse -> {
                                Post post = new Post();

                                post.setPhotoUrl(uriResponse.toString());
                                post.setCaption(caption);
                                post.setTimestamp(System.currentTimeMillis());

                                DocumentReference postReference = FirebaseFirestore.getInstance()
                                        .collection("posts")
                                        .document(uid)
                                        .collection("posts")
                                        .document();
                                postReference.set(post)
                                        .addOnSuccessListener(aVoid -> {
                                            FirebaseFirestore.getInstance()
                                                    .collection("followers")
                                                    .document(uid)
                                                    .collection("followers")
                                                    .get()
                                                    .addOnCompleteListener(task -> {
                                                        if (!task.isSuccessful()) return;

                                                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                                        for (DocumentSnapshot doc : documents) {
                                                            User user = doc.toObject(User.class);
                                                            Feed feed = new Feed();

                                                            feed.setPhotoUrl(post.getPhotoUrl());
                                                            feed.setCaption(post.getCaption());
                                                            feed.setTimestamp(post.getTimestamp());
                                                            feed.setUuid(postReference.getId());
                                                            feed.setPublisher(user);

                                                            FirebaseFirestore.getInstance()
                                                                    .collection("feed")
                                                                    .document(user.getUuid())
                                                                    .collection("posts")
                                                                    .document(postReference.getPath())
                                                                    .set(feed);
                                                        }
                                                    });
                                            String id = postReference.getId();
                                            Feed feed = new Feed();

                                            feed.setPhotoUrl(post.getPhotoUrl());
                                            feed.setCaption(post.getCaption());
                                            feed.setTimestamp(post.getTimestamp());
                                            feed.setUuid(id);

                                            FirebaseFirestore.getInstance()
                                                    .collection("user")
                                                    .document(uid)
                                                    .get()
                                                    .addOnCompleteListener(task -> {
                                                        User user = task.getResult().toObject(User.class);

                                                        feed.setPublisher(user);

                                                        FirebaseFirestore.getInstance()
                                                                .collection("feed")
                                                                .document(uid)
                                                                .collection("posts")
                                                                .document(id)
                                                                .set(feed);
                                                    });
                                            presenter.onSuccess(null);
                                            presenter.onComplete();
                                        })
                                        .addOnFailureListener(e -> presenter.onError(e.getMessage()));
                            })
                            .addOnFailureListener(e -> presenter.onError(e.getMessage()));
                })
                .addOnFailureListener(e -> presenter.onError(e.getMessage()));
    }
}
