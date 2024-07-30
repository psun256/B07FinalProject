package org.group43.finalproject.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.group43.finalproject.Presenter.ArtifactTablePresenter;
import org.group43.finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtifactTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtifactTableFragment extends Fragment {
    private ArtifactTablePresenter presenter;

    public ArtifactTableFragment() {
        // Required empty public constructor
    }

    public static ArtifactTableFragment newInstance() {
        return new ArtifactTableFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artifact_table, container, false);
        presenter = new ArtifactTablePresenter(this);
        RecyclerView recyclerView = view.findViewById(R.id.artifactTable);
        presenter.bindAdapterToView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}