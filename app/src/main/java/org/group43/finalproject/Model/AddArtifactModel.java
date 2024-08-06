package org.group43.finalproject.Model;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddArtifactModel {
    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    public AddArtifactModel() {
        db = null;
        dbRef = null;
    }

    public void artifactExists(Artifact artifact, AddArtifactListener listener) {
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("artifacts/" + artifact.getLotNumber());

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    listener.onError("Lot number is already taken! Artifact not added.");
                } else {
                    listener.onSuccess("Adding artifact...");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void uploadArtifact(Artifact artifact, Uri fileUri, AddArtifactListener listener) {
        if (fileUri == null) {
            uploadArtifactToDB(artifact, listener);
        } else {
            uploadMediaToStorage(artifact, fileUri, listener);
        }
    }

    private void uploadMediaToStorage(Artifact artifact, Uri fileUri, AddArtifactListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference artifactRef;

        if (artifact.getFileType().equals("image")) {
            artifactRef = storageRef.child("img/" + artifact.getFile());
        } else {
            artifactRef = storageRef.child("vid/" + artifact.getFile());
        }

        artifactRef.putFile(fileUri).addOnSuccessListener(taskSnapshot -> artifactRef
                        .getDownloadUrl()
                        .addOnSuccessListener(uri -> uploadArtifactToDB(artifact, listener)))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onError("Error: " + artifact.getFile()
                                + " was not uploaded. Artifact not added.");
                    }
                });
    }

    public void uploadArtifactToDB(Artifact artifact, AddArtifactListener listener) {
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("artifacts/");

        dbRef.child(String.valueOf(artifact.getLotNumber())).setValue(artifact)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess("Artifact successfully added!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onError("Error: Artifact could not be added.");
                    }
                });
    }
}
