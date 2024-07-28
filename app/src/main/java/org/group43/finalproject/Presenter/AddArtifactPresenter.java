package org.group43.finalproject.Presenter;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.Category;
import org.group43.finalproject.R;
import org.group43.finalproject.View.AddArtifactFragment;

import java.util.ArrayList;
import java.util.Objects;

public class AddArtifactPresenter {
    private final AddArtifactFragment view;
    private final ContentResolver contentRes;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    public AddArtifactPresenter(AddArtifactFragment view) {
        this.view = view;
        this.contentRes = view.requireActivity().getContentResolver();
    }

    public ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();

        db = FirebaseDatabase.getInstance("https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");
        dbRef = db.getReference("categories/");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String category = snapshot.getKey();

                    if (!categories.contains(category)) {
                        categories.add(category);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return categories;
    }

    public void filePicked(Uri uri) {
        Cursor cursor = contentRes.query(uri, null, null, null, null);
        int nameIndex = Objects.requireNonNull(cursor).getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();

        String fileName = cursor.getString(nameIndex);
        if (fileName != null) {
            view.showFileInfo(fileName);
            view.showMessage(fileName + " successfully uploaded!");
        } else {
            view.showMessage("Error: Cannot upload file");
        }
    }

    public void addArtifact(Uri fileUri) {
        if (!checkEmptyFields()) {
            return;
        }

        Artifact artifact = createArtifact(fileUri);
        db = FirebaseDatabase.getInstance("https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");
        dbRef = db.getReference("artifacts/" + artifact.getLotNumber());

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    view.showMessage("Lot number is already taken! Artifact not added.");
                } else {
                    if (!artifact.getFile().isEmpty()) {
                        uploadMediaToStorage(fileUri, artifact);
                    } else {
                        uploadArtifactToDB(artifact);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadMediaToStorage(Uri fileUri, Artifact artifact) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference artifactRef;

        if (artifact.getFileType().equals(view.getString(R.string.image))) {
            artifactRef = storageRef.child("img/" + artifact.getFile());
        } else {
            artifactRef = storageRef.child("vid/" + artifact.getFile());
        }

        artifactRef.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot -> artifactRef
                        .getDownloadUrl()
                        .addOnSuccessListener(uri -> uploadArtifactToDB(artifact)))
                .addOnFailureListener(e -> view
                        .showMessage("Error: " + artifact.getFile() + " was not uploaded. Artifact not added."));
    }

    public void uploadArtifactToDB(Artifact artifact) {
        db = FirebaseDatabase.getInstance("https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");
        dbRef = db.getReference("artifacts/");

        dbRef.child(String.valueOf(artifact.getLotNumber()))
                .setValue(artifact)
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                view.showMessage("Artifact successfully added!");
                updateCategories(artifact);
            } else {
                view.showMessage("Error: Artifact was not added.");
            }
        });
    }

    private Artifact createArtifact(Uri fileUri) {
        int lotNum = Integer.parseInt(view.getEditLotNum().getText().toString().trim());
        String name = view.getEditName().getText().toString().trim();
        String category = view.getEditCategory().getText().toString().trim();
        String period = view.getEditPeriod().getSelectedItem().toString().trim();
        String desc = view.getEditDesc().getText().toString().trim();
        String fileName = view.getTextFileName().getText().toString().trim();
        String fileType = "";
        String mimeType;

        if (fileUri != null) {
            mimeType = contentRes.getType(fileUri);
            if (Objects.requireNonNull(mimeType).contains("image")) {
                fileType = "image";
            } else {
                fileType = "video";
            }
        }

        return new Artifact(lotNum, name, category, period, desc, fileName, fileType);
    }

    private boolean checkEmptyFields() {
        if (view.getEditLotNum().getText().toString().trim().isEmpty() ||
                view.getEditName().getText().toString().trim().isEmpty() ||
                view.getEditDesc().getText().toString().trim().isEmpty()) {
            view.showMessage("Please fill out all fields!");
            return false;
        }

        return true;
    }

    private void updateCategories(Artifact artifact) {
        db = FirebaseDatabase.getInstance("https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");
        dbRef = db.getReference("categories/");

        dbRef.child(artifact.getCategory()).setValue(new Category(artifact.getCategory()));
    }

}