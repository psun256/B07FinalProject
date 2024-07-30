package org.group43.finalproject.Model;

import java.util.ArrayList;
import java.util.List;

public class SelectedArtifactsModel {
    private static final List<Artifact> selectedArtifacts = new ArrayList<>();

    public SelectedArtifactsModel() {
    }

    public void selectArtifact(Artifact artifact) {
        selectedArtifacts.add(artifact);
    }

    public void removeArtifact(Artifact artifact) {
        selectedArtifacts.remove(artifact);
    }

    public List<Artifact> getSelectedArtifacts() {
        return selectedArtifacts;
    }
}


