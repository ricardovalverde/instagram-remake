package Main.Profile.DataSource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import common.model.Feed;
import common.model.Post;
import common.model.User;
import common.model.UserProfile;
import common.presenter.Presenter;

public class ProfileFirebaseDataSource implements ProfileDataSource {
    @Override
    public void findUser(String uuid, Presenter<UserProfile> profilePresenter) {
        FirebaseFirestore.getInstance()
                .collection("user")
                .document(uuid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);

                    FirebaseFirestore.getInstance()
                            .collection("posts")
                            .document(uuid)
                            .collection("posts")
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {

                                List<Post> postList = new ArrayList<>();
                                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                                for (DocumentSnapshot doc : documents) {
                                    Post post = doc.toObject(Post.class);
                                    postList.add(post);
                                }
                                FirebaseFirestore.getInstance()
                                        .collection("followers")
                                        .document(uuid)
                                        .collection("followers")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .get()
                                        .addOnSuccessListener(documentSnapshot1 -> {
                                            boolean following = documentSnapshot1.exists();
                                            profilePresenter.onSuccess(new UserProfile(user, postList, following));
                                            profilePresenter.onComplete();
                                        })
                                        .addOnFailureListener(e -> profilePresenter.onError(e.getMessage()));
                            })
                            .addOnFailureListener(e -> profilePresenter.onError(e.getMessage()));
                })
                .addOnFailureListener(e -> profilePresenter.onError(e.getMessage()));
    }

    @Override
    public void follow(String uuid) {
        FirebaseFirestore.getInstance()
                .collection("user")
                .document(uuid)
                .get()
                .addOnCompleteListener(task -> {
                    User user = task.getResult().toObject(User.class);

                    FirebaseFirestore.getInstance()
                            .collection("user")
                            .document(FirebaseAuth.getInstance().getUid())
                            .get()
                            .addOnCompleteListener(task1 -> {
                                User me = task1.getResult().toObject(User.class);

                                FirebaseFirestore.getInstance()
                                        .collection("followers")
                                        .document(uuid)
                                        .collection("followers")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .set(me);
                            });

                    FirebaseFirestore.getInstance()
                            .collection("posts")
                            .document(uuid)
                            .collection("posts")
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                            .limit(10)
                            .get()
                            .addOnCompleteListener(task1 -> {
                                if (task.isSuccessful()) {
                                    List<DocumentSnapshot> documents = task1.getResult().getDocuments();

                                    for (DocumentSnapshot doc : documents) {
                                        Post post = doc.toObject(Post.class);

                                        Feed feed = new Feed();
                                        feed.setPhotoUrl(post.getPhotoUrl());
                                        feed.setCaption(post.getCaption());
                                        feed.setPublisher(user);
                                        feed.setUuid(doc.getId());
                                        feed.setTimestamp(post.getTimestamp());

                                        FirebaseFirestore.getInstance()
                                                .collection("feed")
                                                .document(FirebaseAuth.getInstance().getUid())
                                                .collection("post")
                                                .document(doc.getId())
                                                .set(feed);
                                    }
                                }
                            });
                });
    }

    @Override
    public void unfollow(String uuid) {
        FirebaseFirestore.getInstance()
                .collection("user")
                .document(uuid)
                .get()
                .addOnCompleteListener(task -> {
                    User user = task.getResult().toObject(User.class);

                    FirebaseFirestore.getInstance()
                            .collection("followers")
                            .document(uuid)
                            .collection("followers")
                            .document(FirebaseAuth.getInstance().getUid())
                            .delete();

                    FirebaseFirestore.getInstance()
                            .collection("feed")
                            .document(FirebaseAuth.getInstance().getUid())
                            .collection("posts")
                            .whereEqualTo("publisher.uuid", uuid)
                            .get()
                            .addOnCompleteListener(taskResult -> {

                                if (taskResult.isSuccessful()) {
                                    List<DocumentSnapshot> documents = taskResult.getResult().getDocuments();

                                    for (DocumentSnapshot doc : documents) {
                                        DocumentReference reference = doc.getReference();
                                        reference.delete();
                                    }
                                }
                            });
                });
    }
}
