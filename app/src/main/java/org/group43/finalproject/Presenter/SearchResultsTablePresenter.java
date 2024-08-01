package org.group43.finalproject.Presenter;

import org.group43.finalproject.Model.Artifact;
import org.group43.finalproject.Model.ArtifactsRepository;
import org.group43.finalproject.View.AllArtifactsTableFragment;
import org.group43.finalproject.View.SearchResultsTableFragment;

import java.util.List;

public class SearchResultsTablePresenter {

    private ArtifactsRepository repository;
    private final SearchResultsTableFragment artifactTable;

    public SearchResultsTablePresenter(SearchResultsTableFragment artifactTable) {
        this.artifactTable = artifactTable;
        repository = new ArtifactsRepository();
    }

    public void loadArtifacts() {
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
