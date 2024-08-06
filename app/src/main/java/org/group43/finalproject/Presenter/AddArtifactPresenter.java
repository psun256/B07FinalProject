package org.group43.finalproject.Presenter;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import org.group43.finalproject.Model.AddArtifactListener;
import org.group43.finalproject.Model.AddArtifactModel;
import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.CategoryModel;
import org.group43.finalproject.R;
import org.group43.finalproject.View.AddArtifactFragment;

import java.util.Objects;

public class AddArtifactPresenter {
    private final AddArtifactFragment view;
    private final AddArtifactModel model;
    private final ContentResolver contentRes;

    public AddArtifactPresenter(AddArtifactFragment view) {
        this.view = view;
        model = new AddArtifactModel();
        this.contentRes = view.requireActivity().getContentResolver();
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

    public void clearArtifact() {
        view.getEditLotNum().getText().clear();
        view.getEditName().getText().clear();
        view.getEditCategory().getText().clear();
        view.getEditDesc().getText().clear();
        view.getTextFileName().setText("");
        view.getTextFileName().setHint(view.getResources().getString(R.string.defaultFile));
        view.showMessage("Input cleared!");
    }

    public void addArtifact(Uri fileUri) {
        if (!checkEmptyFields()) {
            view.showMessage("Please fill out all fields!");
            return;
        }

        Artifact artifact = createArtifact(fileUri);
        model.artifactExists(artifact, new AddArtifactListener() {
            @Override
            public void onSuccess(String message) {
                view.showMessage(message);
                uploadArtifact(artifact, fileUri);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
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

    private void uploadArtifact(Artifact artifact, Uri fileUri) {
        model.uploadArtifact(artifact, fileUri, new AddArtifactListener() {
            @Override
            public void onSuccess(String message) {
                view.showMessage(message);
                CategoryModel.getInstance().updateCategories(artifact);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        });
    }

    private boolean checkEmptyFields() {
        return !view.getEditLotNum().getText().toString().trim().isEmpty() &&
                !view.getEditName().getText().toString().trim().isEmpty() &&
                !view.getEditCategory().getText().toString().trim().isEmpty() &&
                !view.getEditDesc().getText().toString().trim().isEmpty();
    }
}