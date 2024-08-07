package org.group43.finalproject.Model;

import android.annotation.SuppressLint;
import android.util.Log;

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
        artifactsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lot = SearchParamsModel.getLot();
                String name = SearchParamsModel.getName();
                String category = SearchParamsModel.getCategory();
                String period = SearchParamsModel.getPeriod();
                List<Artifact> artifacts = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Artifact artifact = snapshot.getValue(Artifact.class);
                    if (SearchParamsModel.matchesInSearch(artifact,lot,name,category,period)) {
                        artifacts.add(artifact);
                    }
                }
                callback.onSuccess(artifacts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.toException());
            }
        });
    }

    public interface DataCallback<T> {
        void onSuccess(T data);
        void onFailure(Exception e);
    }
}