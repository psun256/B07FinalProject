package org.group43.finalproject.Presenter;

import android.annotation.SuppressLint;
import android.content.ContentResolver;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.ArtifactAdapter;
import org.group43.finalproject.View.ArtifactTableFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ArtifactTablePresenter {
    private static ArrayList<Artifact> artifacts;
    private static ArtifactAdapter artifactAdapter;
    private static FirebaseDatabase db;
    private ArtifactTableFragment artifactTable;

    public ArtifactTablePresenter(ArtifactTableFragment artifactTable) {
        this.artifactTable = artifactTable;

        if (artifacts == null) {
            artifacts = new ArrayList<>();
        }
        if (artifactAdapter == null) {
            artifactAdapter = new ArtifactAdapter(artifacts);
        }
        if (db == null) {
            db = Objects.requireNonNull(
                    FirebaseDatabase.getInstance(
                    "https://b07finalproject-81ec0-default-rtdb.firebaseio.com/"
                    )
            );
        }
        DatabaseReference artifactsRef = db.getReference("artifacts/");

        artifactsRef.orderByChild("lotNumber").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                artifacts.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Artifact artifact = Objects.requireNonNull(snapshot.getValue(Artifact.class));
                    artifacts.add(artifact);
                }
                artifactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    public void bindAdapterToView(RecyclerView recyclerView) {
        recyclerView.setAdapter(artifactAdapter);
    }
}
