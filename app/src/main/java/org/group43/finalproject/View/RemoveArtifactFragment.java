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
        removePresenter = new RemoveArtifactPresenter(getContext(), new RemoveArtifactPresenter.RemovalListener() {
            @Override
            public void onRemovalComplete() {
                navigateBack();
            }

            @Override
            public void onRemovalFailed() {
                navigateBack();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remove_artifact, container, false);

        removePresenter.confirmAndRemove();

        return view;
    }


    private void navigateBack() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }



}

