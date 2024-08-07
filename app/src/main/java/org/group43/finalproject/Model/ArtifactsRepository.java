package org.group43.finalproject.Model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtifactsRepository {
    private final DatabaseReference artifactsRef;

    public ArtifactsRepository() {
        artifactsRef = FirebaseDatabase.getInstance().getReference("artifacts/");
    }

    public void getAllArtifacts(DataCallback<List<Artifact>> callback) {
        artifactsRef.orderByChild("lotNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Artifact> artifacts = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Artifact artifact = Objects.requireNonNull(snapshot.getValue(Artifact.class));
                    artifacts.add(artifact);
                }
                callback.onSuccess(artifacts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    public void searchArtifacts(DataCallback<List<Artifact>> callback) {
        //TODO: add search parameters above and put your searching algorithm here!
    }

    public interface DataCallback<T> {
        void onSuccess(T data);
        void onFailure(Exception e);
    }
}
