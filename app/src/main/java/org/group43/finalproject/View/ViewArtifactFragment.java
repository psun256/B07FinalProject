package org.group43.finalproject.View;

import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.SelectedArtifactsModel;
import org.group43.finalproject.R;
import java.util.List;

public class ViewArtifactFragment extends Fragment {

    private TextView lotNumber;
    private TextView name;
    private TextView category;
    private TextView period;
    private TextView description;
    private ImageView image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_artifact, container, false);

        lotNumber = view.findViewById(R.id.detailViewLotNumber);
        name = view.findViewById(R.id.detailViewName);
        category = view.findViewById(R.id.detailViewCategory);
        period = view.findViewById(R.id.detailViewPeriod);
        description = view.findViewById(R.id.detailViewDescription);
        image = view.findViewById(R.id.detailViewImage);

        // Fetch the selected artifact
        List<Artifact> artifacts = SelectedArtifactsModel.getSelectedArtifacts();


        if (artifacts != null && !artifacts.isEmpty()) {
            Artifact artifact = artifacts.get(0);
            lotNumber.setText(String.valueOf(artifact.getLotNumber()));
            name.setText(artifact.getName());
            category.setText(artifact.getCategory());
            period.setText(artifact.getPeriod());
            description.setText(artifact.getDescription());

            // Add image getter.
        }

        return view;
    }
}
