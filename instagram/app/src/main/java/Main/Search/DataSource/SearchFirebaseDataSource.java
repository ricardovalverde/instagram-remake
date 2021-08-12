package Main.Search.DataSource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import common.model.User;
import common.presenter.Presenter;

public class SearchFirebaseDataSource implements SearchDataSource {
    @Override
    public void findUsers(String query, Presenter<List<User>> presenter) {
        FirebaseFirestore.getInstance()
                .collection("user")
                .whereEqualTo("name", query)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<User> userList = new ArrayList<>();
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot doc : documents) {

                        User user = doc.toObject(User.class);

                        if (!user.getUuid().equals(FirebaseAuth.getInstance().getUid())) {
                            userList.add(user);
                        }
                    }
                    presenter.onSuccess(userList);
                })
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(task -> presenter.onComplete());
    }
}
