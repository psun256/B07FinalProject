package org.group43.finalproject.Presenter;

import android.widget.Toast;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.ArtifactAdapter;
import org.group43.finalproject.Model.SelectedArtifactsModel;

public class ArtifactViewHolderPresenter {
    private final ArtifactAdapter.ArtifactViewHolder holder;
    private Artifact artifact;
    private final SelectedArtifactsModel selectedArtifactsModel;

    public ArtifactViewHolderPresenter(ArtifactAdapter.ArtifactViewHolder holder) {
        this.holder = holder;
        selectedArtifactsModel = new SelectedArtifactsModel();
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
        holder.displayArtifact(
                artifact,
                selectedArtifactsModel.getSelectedArtifacts().contains(artifact)
        );
    }

    public void onSelect() {
        selectedArtifactsModel.selectArtifact(artifact);
    }

    public void onDeselect() {
        selectedArtifactsModel.deselectArtifact(artifact);
    }
}
