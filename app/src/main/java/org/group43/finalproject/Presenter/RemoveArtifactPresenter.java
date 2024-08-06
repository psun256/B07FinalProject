package org.group43.finalproject.Presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.SelectedArtifactModel;

public class RemoveArtifactPresenter {
    private Context context;

    public RemoveArtifactPresenter(Context context) {
        this.context = context;
    }

    public void confirmAndRemove() {
        Artifact artifact = SelectedArtifactModel.getSelectedArtifact();
        if (artifact != null) {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm Removal")
                    .setMessage("Are you sure you want to remove this artifact: " + artifact.toString() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> removeArtifact(String.valueOf(artifact.getLotNumber())))
                    .setNegativeButton("No", null)
                    .show();
        } else {
            Toast.makeText(context, "No artifact selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeArtifact(String artifactId) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("artifacts");
        dbRef.child(artifactId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Artifact artifact = snapshot.getValue(Artifact.class);
                if (artifact != null && artifact.getFile() != null && !artifact.getFile().isEmpty()) {
                    deleteFileFromStorage(artifact.getFile(), artifact.getFileType(), artifactId);
                } else {
                    deleteArtifactFromDatabase(artifactId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Failed to retrieve artifact details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteFileFromStorage(String fileName, String fileType, String artifactId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference fileRef = storageRef.child((fileType.equals("image") ? "img/" : "vid/") + fileName);

        fileRef.delete().addOnSuccessListener(aVoid -> {
            Toast.makeText(context, "File deleted successfully.", Toast.LENGTH_SHORT).show();
            deleteArtifactFromDatabase(artifactId);
        }).addOnFailureListener(e -> Toast.makeText(context, "Failed to delete file.", Toast.LENGTH_SHORT).show());
    }

    private void deleteArtifactFromDatabase(String artifactId) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("artifacts");
        dbRef.child(artifactId).removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Artifact removed successfully.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to remove artifact.", Toast.LENGTH_SHORT).show());
    }
}
