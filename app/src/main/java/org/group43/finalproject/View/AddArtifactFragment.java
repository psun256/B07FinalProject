package org.group43.finalproject.View;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
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

import org.group43.finalproject.Model.CategoryModel;
import org.group43.finalproject.Presenter.AddArtifactPresenter;
import org.group43.finalproject.R;

import java.util.Objects;

public class AddArtifactFragment extends Fragment {
    private Toolbar addArtifactToolbar;

    private EditText editLotNum;
    private EditText editName;
    private EditText editDesc;
    private AutoCompleteTextView editCategory;
    private Spinner editPeriod;
    private TextView textFileName;

    private Button addButton;
    private Button uploadButton;
    private Button clearAllButton;

    private AddArtifactPresenter addArtifactPresenter;
    private Uri fileUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_artifact, container, false);
        addArtifactPresenter = new AddArtifactPresenter(this);
        fileUri = null;

        initializeView(view);
        initializeToolbar();
        initializeCategoryMenu();

        uploadButton.setOnClickListener(v -> chooseFile());
        addButton.setOnClickListener(v -> addArtifactPresenter.addArtifact(fileUri));
        clearAllButton.setOnClickListener(v -> {
            fileUri = null;
            addArtifactPresenter.clearArtifact();
        });

        return view;
    }

    public void initializeView(View view) {
        addArtifactToolbar = view.findViewById(R.id.addArtifactToolbar);
        editLotNum = view.findViewById(R.id.editLotNum);
        editName = view.findViewById(R.id.editName);
        editDesc = view.findViewById(R.id.editDescription);
        editCategory = view.findViewById(R.id.editCategory);
        editPeriod = view.findViewById(R.id.editPeriod);
        textFileName = view.findViewById(R.id.textFileName);
        addButton = view.findViewById(R.id.addButton);
        uploadButton = view.findViewById(R.id.uploadButton);
        clearAllButton = view.findViewById(R.id.clearAllButton);
    }

    public void initializeToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Objects.requireNonNull(activity).setSupportActionBar(addArtifactToolbar);

        if (activity.getSupportActionBar() != null) {
            activity.setSupportActionBar(addArtifactToolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeButtonEnabled(true);
            Objects.requireNonNull(addArtifactToolbar.getNavigationIcon())
                    .setColorFilter(ContextCompat.getColor(requireContext(),
                            R.color.backgroundLight), PorterDuff.Mode.SRC_IN);
        }
        addArtifactToolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initializeCategoryMenu() {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this.requireContext(),
                android.R.layout.simple_dropdown_item_1line, CategoryModel.getInstance().getCategories());
        editCategory.setAdapter(categoryAdapter);

        editCategory.setOnTouchListener((view, motionEvent) -> {
            editCategory.showDropDown();
            return false;
        });
    }

    public void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        String[] types = {"image/*", "video/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types);
        pickFile.launch(intent);
    }

    private final ActivityResultLauncher<Intent> pickFile = registerForActivityResult(new ActivityResultContracts
            .StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            fileUri = result.getData().getData();
            addArtifactPresenter.filePicked(fileUri);
        }
    });

    public void showFileInfo(String fileName) {
        if (!fileName.isEmpty()) {
            textFileName.setText(fileName);
        }
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public EditText getEditLotNum() {
        return editLotNum;
    }

    public EditText getEditName() {
        return editName;
    }

    public AutoCompleteTextView getEditCategory() {
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
