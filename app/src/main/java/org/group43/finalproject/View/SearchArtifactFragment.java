package org.group43.finalproject.View;

//import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.group43.finalproject.R;

//import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchArtifactFragment extends Fragment {
    @Nullable
    ArrayList<DatabaseReference> validArtifacts = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_artifact, container, false);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://b07finalproject-81ec0-default-rtdb.firebaseio.com/");
        DatabaseReference dbRef = db.getReference("artifacts/");

        EditText lotValue = getView().findViewById(R.id.searchLotNum);
        EditText nameValue = getView().findViewById(R.id.searchName);
        EditText categoryValue = getView().findViewById(R.id.searchCategory);
        EditText periodValue = getView().findViewById(R.id.searchPeriod);

        String lot = lotValue.getText().toString();
        String name = nameValue.getText().toString();
        String category = categoryValue.getText().toString();
        String period = periodValue.getText().toString();

        //This is the list of all artifacts that will be displayed as the result of the search
        Button resultButton = getView().findViewById(R.id.searchButton);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchArtifacts(dbRef, lot, name, category, period);
            }
        });
        return view;
    }

    public void searchArtifacts(DatabaseReference dbRef, String lot, String name, String category, String period) {
        for (DataSnapshot artifact : dbRef.get().getResult().getChildren()) {
            String artifactNum = (String) artifact.child("LotNumber").getValue();
            String artifactName = (String) artifact.child("Name").getValue();
            String artifactCategory = (String) artifact.child("Category").getValue();
            String artifactPeriod = (String) artifact.child("Period").getValue();
            if ((lot.isEmpty() || artifactNum.equals(lot))
                    && (name.isEmpty() || artifactName.contains(name))
                    && (category.isEmpty() || artifactCategory.equals(category))
                    && (period.isEmpty() || artifactPeriod.equals(period))) {
                validArtifacts.add(artifact.getRef());
            }
        }
    }
}
