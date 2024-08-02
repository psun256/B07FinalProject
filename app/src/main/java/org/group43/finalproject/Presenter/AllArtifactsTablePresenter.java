package org.group43.finalproject.Presenter;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.ArtifactsRepository;
import org.group43.finalproject.Model.SelectedArtifactModel;
import org.group43.finalproject.View.AllArtifactsTableFragment;

import java.util.List;

public class AllArtifactsTablePresenter {

    private final ArtifactsRepository repository;
    private final AllArtifactsTableFragment artifactTable;

    public AllArtifactsTablePresenter(AllArtifactsTableFragment artifactTable) {
        this.artifactTable = artifactTable;
        repository = new ArtifactsRepository();
    }

    public void loadArtifacts() {
        new SelectedArtifactModel().clearSelection();
        repository.getAllArtifacts(
                new ArtifactsRepository.DataCallback<List<Artifact>>() {
                    @Override
                    public void onSuccess(List<Artifact> data) {
                        artifactTable.displayArtifacts(data);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        artifactTable.showError(e.getMessage());
                    }
                }
        );
    }
}
