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

        holder.getLotNumText().setText(String.valueOf(artifact.getLotNumber()));
        holder.getNameText().setText(artifact.getName());
        holder.getCategoryText().setText(artifact.getCategory());
        holder.getPeriodText().setText(artifact.getPeriod());

        holder.getSelected().setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) {
                onSelect();
            } else {
                onRemove();
            }
        });
    }

    public void onSelect() {
        selectedArtifactsModel.selectArtifact(artifact);
        //FIXME: The toast is for debug purpose only! remove it after debugging is done.

        Toast.makeText(
                holder.getSelected().getContext(),
                "selected Lot# " + artifact.getLotNumber()
                        + "\nTotal selected: "
                        + selectedArtifactsModel.getSelectedArtifacts().size(),
                Toast.LENGTH_SHORT
        ).show();
    }

    public void onRemove() {
        selectedArtifactsModel.removeArtifact(artifact);
        //FIXME: The toast is for debug purpose only! remove it after debugging is done.
        Toast.makeText(
                holder.getSelected().getContext(),
                "removed Lot# " + artifact.getLotNumber()
                        + "\nTotal selected: "
                        + selectedArtifactsModel.getSelectedArtifacts().size(),
                Toast.LENGTH_SHORT
        ).show();
    }
}
