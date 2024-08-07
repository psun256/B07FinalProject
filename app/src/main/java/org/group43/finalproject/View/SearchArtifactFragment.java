package org.group43.finalproject.View;

import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.group43.finalproject.R;
import org.group43.finalproject.Model.SearchParamsModel;

public class SearchArtifactFragment extends Fragment {
    EditText lotText;
    EditText nameText;
    EditText categoryText;
    EditText periodText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_artifact, container, false);
        lotText = view.findViewById(R.id.searchLotNum);
        nameText = view.findViewById(R.id.searchName);
        categoryText = view.findViewById(R.id.searchCategory);
        periodText = view.findViewById(R.id.searchPeriod);
        Button resultButton = view.findViewById(R.id.searchButton);
        Button backButton = view.findViewById(R.id.backButton);
        Button viewButton = view.findViewById(R.id.viewButton);
        SearchResultsTableFragment table = (SearchResultsTableFragment) getChildFragmentManager().findFragmentById(R.id.fragmentSearchTable);

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSearchParams();
                table.refresh();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSearchParams();
                loadFragment(new HomeFragment());
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new ViewArtifactFragment());}
        });

        return view;
    }

    protected void resetSearchParams() {
        SearchParamsModel.setLot("");
        SearchParamsModel.setName("");
        SearchParamsModel.setCategory("");
        SearchParamsModel.setPeriod("");

    }

    protected void setSearchParams() {
        SearchParamsModel.setLot(lotText.getText().toString());
        SearchParamsModel.setName(nameText.getText().toString());
        SearchParamsModel.setCategory(categoryText.getText().toString());
        SearchParamsModel.setPeriod(periodText.getText().toString());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
