package org.group43.finalproject.Presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        dbRef.child(artifactId).removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Artifact removed successfully.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to remove artifact.", Toast.LENGTH_SHORT).show());
    }
}
