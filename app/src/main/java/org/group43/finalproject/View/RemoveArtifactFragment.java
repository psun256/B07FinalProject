package org.group43.finalproject.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.content.ContextCompat;
import android.graphics.PorterDuff;
import java.util.Objects;

import org.group43.finalproject.Model.SelectedArtifactModel;
import org.group43.finalproject.Presenter.RemoveArtifactPresenter;
import org.group43.finalproject.R;

public class RemoveArtifactFragment extends Fragment {
    private RemoveArtifactPresenter removePresenter;
    private Toolbar removeArtifactToolbar;

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

        initializeView(view);
        initializeToolbar();

        removeButton.setOnClickListener(v -> {
            removePresenter.confirmAndRemove();
        });

        return view;
    }

    private void initializeView(View view) {
        removeArtifactToolbar = view.findViewById(R.id.removeArtifactToolbar);
    }

    private void initializeToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(removeArtifactToolbar);
            Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeButtonEnabled(true);

            if (removeArtifactToolbar.getNavigationIcon() != null) {
                int color = ContextCompat.getColor(requireContext(), R.color.backgroundLight);
                removeArtifactToolbar.getNavigationIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }

            removeArtifactToolbar.setNavigationOnClickListener(v -> activity.onBackPressed());
        }

    }
}

