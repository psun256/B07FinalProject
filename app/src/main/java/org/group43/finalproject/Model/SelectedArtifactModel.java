package org.group43.finalproject.Model;

import androidx.annotation.Nullable;

public class SelectedArtifactModel {
    private static Artifact selectedArtifact = null;

    public SelectedArtifactModel() {
    }

    public void selectArtifact(Artifact artifact) {
        selectedArtifact = artifact;
    }

    public void clearSelection() {
        selectedArtifact = null;
    }

    public static @Nullable Artifact getSelectedArtifact() {
        return selectedArtifact;
    }
}


