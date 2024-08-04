package org.group43.finalproject.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.group43.finalproject.Model.SelectedArtifactModel;
import org.group43.finalproject.Presenter.RemoveArtifactPresenter;
import org.group43.finalproject.R;

public class RemoveArtifactFragment extends Fragment {
    private RemoveArtifactPresenter removePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removePresenter = new RemoveArtifactPresenter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remove_artifact, container, false);
        Button removeButton = view.findViewById(R.id.btnRemoveArtifact);

        removeButton.setOnClickListener(v -> {
            removePresenter.confirmAndRemove();
        });

        return view;
    }
}
