package org.group43.finalproject.Presenter;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.ArtifactsRepository;
import org.group43.finalproject.Model.SelectedArtifactModel;
import org.group43.finalproject.View.SearchResultsTableFragment;

import java.util.List;

public class SearchResultsTablePresenter {

    private final ArtifactsRepository repository;
    private final SearchResultsTableFragment artifactTable;

    public SearchResultsTablePresenter(SearchResultsTableFragment artifactTable) {
        this.artifactTable = artifactTable;
        repository = new ArtifactsRepository();
    }

    public void loadArtifacts() {
        new SelectedArtifactModel().clearSelection();
        repository.searchArtifacts(
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
