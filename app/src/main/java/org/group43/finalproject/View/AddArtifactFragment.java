package org.group43.finalproject.View;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.group43.finalproject.Presenter.AddArtifactPresenter;
import org.group43.finalproject.R;

import java.util.Objects;

public class AddArtifactFragment extends Fragment {
    private Toolbar addArtifactToolbar;

    private EditText editLotNum;
    private EditText editName;
    private EditText editDesc;

    private TextView textFileName;

    private Button addButton;
    private Button uploadButton;

    private Spinner editCategory;
    private Spinner editPeriod;

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    private AddArtifactPresenter addArtifactPresenter;

    private Uri fileUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_artifact, container, false);

        addArtifactToolbar = view.findViewById(R.id.addArtifactToolbar);
        editLotNum = view.findViewById(R.id.editLotNum);
        editName = view.findViewById(R.id.editName);
        editDesc = view.findViewById(R.id.editDescription);
        textFileName = view.findViewById(R.id.textFileName);
        addButton = view.findViewById(R.id.addButton);
        uploadButton = view.findViewById(R.id.uploadButton);
        editCategory = view.findViewById(R.id.editCategory);
        editPeriod = view.findViewById(R.id.editPeriod);
        fileUri = null;

        addArtifactPresenter = new AddArtifactPresenter(this, db, dbRef);
        db = FirebaseDatabase.getInstance("https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");

        //set up drop-down menu for category + period
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(getContext(),
                R.array.categories, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCategory.setAdapter(adapterCategory);

        ArrayAdapter<CharSequence> adapterPeriod = ArrayAdapter.createFromResource(getContext(),
                R.array.periods, android.R.layout.simple_spinner_item);
        adapterPeriod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editPeriod.setAdapter(adapterPeriod);

        //set up toolbar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Objects.requireNonNull(activity).setSupportActionBar(addArtifactToolbar);

        if (activity.getSupportActionBar() != null) {
            activity.setSupportActionBar(addArtifactToolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeButtonEnabled(true);
            activity.getSupportActionBar().setTitle("Add Artifact");
            Objects.requireNonNull(addArtifactToolbar.getNavigationIcon()).setColorFilter(ContextCompat.getColor(getContext(),
                    R.color.backgroundLight), PorterDuff.Mode.SRC_IN);
        }

        addArtifactToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        uploadButton.setOnClickListener(v -> chooseFile());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtifactPresenter.uploadArtifactToDB(fileUri);
            }
        });

        return view;
    }

    public void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        String[] types = {"image/*", "video/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types);
        pickFile.launch(intent);
    }

    private final ActivityResultLauncher<Intent> pickFile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    fileUri = result.getData().getData();
                    addArtifactPresenter.filePicked(fileUri);
                }
            });

    public void showFileName(String fileName) {
        if (!fileName.isEmpty()) {
            textFileName.setText(fileName);
        } else {
            textFileName.setText(getResources().getString(R.string.defaultFile));
        }
    }

    public void showUploadError() {
        Toast.makeText(getContext(), "Error - File not uploaded", Toast.LENGTH_SHORT).show();
    }

    public void showUploadSuccess() {
        Toast.makeText(getContext(), "File successfully uploaded!", Toast.LENGTH_SHORT).show();
    }

    public void showIncompleteFields() {
        Toast.makeText(getContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();
    }

    public void showInvalidFile() {
        Toast.makeText(getContext(), "Please upload an image or video file!", Toast.LENGTH_SHORT).show();
    }

    public void showInvalidLotNumber() {
        Toast.makeText(getContext(), "Lot number is already taken", Toast.LENGTH_SHORT).show();
    }

    public EditText getEditLotNum() {
        return editLotNum;
    }

    public EditText getEditName() {
        return editName;
    }

    public Spinner getEditCategory() {
        return editCategory;
    }

    public Spinner getEditPeriod() {
        return editPeriod;
    }

    public EditText getEditDesc() {
        return editDesc;
    }

    public TextView getTextFileName() {
        return textFileName;
    }
}
