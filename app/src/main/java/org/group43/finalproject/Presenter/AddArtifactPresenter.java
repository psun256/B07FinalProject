package org.group43.finalproject.Presenter;

import android.content.ContentResolver;
import android.net.Uri;

import androidx.documentfile.provider.DocumentFile;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.R;
import org.group43.finalproject.View.AddArtifactFragment;

import java.util.Objects;

public class AddArtifactPresenter {
    private final AddArtifactFragment view;
    private ContentResolver contentRes;

    public AddArtifactPresenter(AddArtifactFragment view) {
        this.view = view;
        this.contentRes = view.requireActivity().getContentResolver();
    }

    public void filePicked(Uri uri) {
        if (uri == null) {
            view.showUploadError();
            return;
        }

        view.showFileName(getFileName(uri));
        view.showUploadSuccess();
    }

    public void uploadArtifactToDB(Uri fileUri) {
        Artifact artifact = createArtifact(fileUri);

        if (artifact == null) {
            view.showUploadError();
            return;
        }

        uploadMediaToStorage(fileUri, artifact);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");
        DatabaseReference dbRef = db.getReference("artifacts/" + artifact.getLotNumber());
        String id = dbRef.push().getKey();

        dbRef.child(Objects.requireNonNull(id)).setValue(artifact).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                view.showUploadSuccess();
            } else {
                view.showUploadError();
            }
        });

    }

    public String getFileName(Uri uri) {
        DocumentFile documentFile;

        if ("content".equals(uri.getScheme())) {
            documentFile = DocumentFile.fromSingleUri(view.requireActivity(), uri);
            return Objects.requireNonNull(documentFile).getName();
        }

        return null;
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

        artifactRef.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {
                    artifactRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        //String downloadUrl = uri.toString();
                        view.showUploadSuccess();
                    });
                })
                .addOnFailureListener(e -> {
                    view.showUploadError();
                });
    }

    private Artifact createArtifact(Uri fileUri) {
        if (!checkArtifactValidity(fileUri)) {
            return null;
        }

        int lotNum = Integer.parseInt(view.getEditLotNum().getText().toString().trim());
        String name = view.getEditName().getText().toString().trim();
        String category = view.getEditCategory().getSelectedItem().toString().trim();
        String period = view.getEditPeriod().getSelectedItem().toString().trim();
        String desc = view.getEditDesc().getText().toString().trim();
        String fileName = view.getTextFileName().getText().toString().trim();
        String mimeType = contentRes.getType(fileUri);
        String fileType;

        if (Objects.requireNonNull(mimeType).contains("image")) {
            fileType = "image";
        } else {
            fileType = "video";
        }

        return new Artifact(lotNum, name, category, period, desc, fileName, fileType);
    }

    private boolean checkArtifactValidity(Uri fileUri) {
        String mimeType = contentRes.getType(fileUri);

        if (view.getEditLotNum().getText().toString().trim().isEmpty() ||
                view.getEditName().getText().toString().trim().isEmpty() ||
                view.getEditDesc().getText().toString().trim().isEmpty() ||
                view.getTextFileName().getText().toString().trim().isEmpty()) {
            view.showIncompleteFields();
            return false;
        }
        if (mimeType == null || !Objects.requireNonNull(mimeType).contains(view.getString(R.string.image)) &&
                !mimeType.contains(view.getString(R.string.video))) {
            view.showInvalidFile();
            return false;
        }
        if (lotNumExists(Integer.parseInt(view.getEditLotNum().getText().toString().trim()))) {
            view.showInvalidLotNumber();
            return false;
        }

        return true;
    }

    private boolean lotNumExists(int lotNum) {
        // check if an artifact w lot number lotNum already exists

        return false;
    }

}
