package org.group43.finalproject.Presenter;

import android.content.ContentResolver;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.group43.finalproject.View.ArtifactTableFragment;

public class ArtifactTablePresenter {
    private ArtifactTableFragment table;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    public ArtifactTablePresenter(ArtifactTableFragment table) {
        this.table = table;
        db = FirebaseDatabase.getInstance(
                "https://b07finalproject-81ec0-default-rtdb.firebaseio.com/"
        );
    }


}
