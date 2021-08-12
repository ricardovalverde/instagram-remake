package Register.datasource;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import common.model.User;
import common.presenter.Presenter;

public class RegisterFirebaseDataSource implements RegisterDataSource {
    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    User user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setUuid(authResult.getUser().getUid());

                    FirebaseFirestore.getInstance().collection("user")
                            .document(authResult.getUser().getUid())
                            .set(user)
                            .addOnSuccessListener(aVoid -> presenter.onSuccess(authResult.getUser()))
                            .addOnCompleteListener(task -> presenter.onComplete());
                })
                .addOnFailureListener(e -> {
                    presenter.onError(e.getMessage());
                    presenter.onComplete();
                });
    }

    @Override
    public void addPhoto(Uri uri, Presenter presenter) {
        String uuid = FirebaseAuth.getInstance().getUid();
        if (uuid == null || uri == null || uri.getPathSegments() == null) {
            return;
        }
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageReference = storageReference
                .child("images/")
                .child(FirebaseAuth.getInstance().getUid())
                .child(uri.getLastPathSegment());
        imageReference.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageReference.getDownloadUrl()
                            .addOnSuccessListener(uriResponse -> {
                                DocumentReference documentReference = FirebaseFirestore.getInstance()
                                        .collection("user")
                                        .document(uuid);
                                documentReference.get()
                                        .addOnSuccessListener(documentTask -> {
                                            User user = documentTask.toObject(User.class);
                                            user.setUrlPhoto(uriResponse.toString());
                                            documentReference.set(user)
                                                    .addOnSuccessListener(aVoid -> {
                                                        presenter.onSuccess(true);
                                                        presenter.onComplete();
                                                    });
                                        });
                            });
                });

    }
}
