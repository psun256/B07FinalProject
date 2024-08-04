package org.group43.finalproject.View;

import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSearchParams();
                refreshTable();
            }
        });
        return view;
    }

    protected void setSearchParams() {
        SearchParamsModel.setLot(lotText.getText().toString());
        SearchParamsModel.setName(nameText.getText().toString());
        SearchParamsModel.setCategory(categoryText.getText().toString());
        SearchParamsModel.setPeriod(periodText.getText().toString());
    }

    protected void refreshTable() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        getActivity().getSupportFragmentManager().beginTransaction().attach(this).commitAllowingStateLoss();
    }
}
