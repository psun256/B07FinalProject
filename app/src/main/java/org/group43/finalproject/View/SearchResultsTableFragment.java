package org.group43.finalproject.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.ArtifactAdapter;
import org.group43.finalproject.Presenter.AllArtifactsTablePresenter;
import org.group43.finalproject.Presenter.SearchResultsTablePresenter;
import org.group43.finalproject.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultsTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsTableFragment extends Fragment {
    private SearchResultsTablePresenter presenter;
    private ArtifactAdapter artifactAdapter;

    public SearchResultsTableFragment() {
        // required empty constructor
    }

    public static SearchResultsTableFragment newInstance() {
        return new SearchResultsTableFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artifact_table, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.artifactTable);

        artifactAdapter = new ArtifactAdapter();
        recyclerView.setAdapter(artifactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter = new SearchResultsTablePresenter(this);
        presenter.loadArtifacts();

        return view;
    }

    public void displayArtifacts(List<Artifact> artifacts) {
        artifactAdapter.setArtifactsToDisplay(artifacts);
    }

    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}