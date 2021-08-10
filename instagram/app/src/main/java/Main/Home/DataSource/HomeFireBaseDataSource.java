package Main.Home.DataSource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import common.model.Feed;
import common.presenter.Presenter;

public class HomeFireBaseDataSource implements HomeDataSource {
    @Override
    public void findFeed(Presenter<List<Feed>> presenter) {
        String uuid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance()
                .collection("feed")
                .document(uuid)
                .collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Feed> feedList = new ArrayList<>();
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot document : documents) {
                        Feed feed = document.toObject(Feed.class);
                        feedList.add(feed);
                    }
                    presenter.onSuccess(feedList);
                })
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(aVoid -> presenter.onComplete());
    }
}
