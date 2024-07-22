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
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.group43.finalproject.R;

public class AddArtifactFragment extends Fragment {
    private Toolbar addArtifactToolbar;

    private EditText editLotNum;
    private EditText editName;

    private TextView fileName;

    private Button uploadPicVid;

    private Spinner editCategory;
    private Spinner editPeriod;

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_artifact, container, false);

        addArtifactToolbar = view.findViewById(R.id.addArtifactToolbar);
        editLotNum = view.findViewById(R.id.editLotNum);
        editName = view.findViewById(R.id.editName);
        fileName = view.findViewById(R.id.fileName);
        uploadPicVid = view.findViewById(R.id.uploadPicVid);
        editCategory = view.findViewById(R.id.editCategory);
        editPeriod = view.findViewById(R.id.editPeriod);

        db = FirebaseDatabase.getInstance("https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");

        //set up spinners
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

        if (activity != null) {
           activity.setSupportActionBar(addArtifactToolbar);
           activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           activity.getSupportActionBar().setHomeButtonEnabled(true);
           activity.getSupportActionBar().setTitle("Add Artifact");
           addArtifactToolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(getContext(), R.color.backgroundLight), PorterDuff.Mode.SRC_IN);

        }

        addArtifactToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //set up upload pic/vid
        uploadPicVid.setOnClickListener(v -> chooseFile());

        return view;
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        String[] types = {"image/*", "video/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types);
        pickFile.launch(intent);

    }

    private final ActivityResultLauncher<Intent> pickFile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri uri = result.getData().getData();
                if (uri != null) {
                    fileName.setText(getFileName(uri));
                }
            }
        });

    private String getFileName(Uri uri) {
        String result = null;

        if ("content".equals(uri.getScheme())) {
            DocumentFile docuFile = DocumentFile.fromSingleUri(getContext(), uri);
            if (docuFile != null) {
                result = docuFile.getName();
            }
        }

        return result;
    }

}
