package org.group43.finalproject.Presenter;

import android.annotation.SuppressLint;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.ArtifactAdapter;
import org.group43.finalproject.Model.SelectedArtifactModel;

public class ArtifactViewHolderPresenter {
    private final ArtifactAdapter.ArtifactViewHolder holder;
    private ArtifactAdapter artifactAdapter;
    private Artifact artifact;
    private final SelectedArtifactModel selectedArtifactsModel;

    public ArtifactViewHolderPresenter(ArtifactAdapter.ArtifactViewHolder holder) {
        this.holder = holder;
        selectedArtifactsModel = new SelectedArtifactModel();
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
        holder.displayArtifact(
                artifact,
                artifact.equals(selectedArtifactsModel.getSelectedArtifact())
        );
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onSelect() {
        selectedArtifactsModel.selectArtifact(artifact);
        artifactAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onDeselect() {
        selectedArtifactsModel.clearSelection();
        artifactAdapter.notifyDataSetChanged();
    }

    public void setAdapter(ArtifactAdapter artifactAdapter) {
        this.artifactAdapter = artifactAdapter;
    }
}
