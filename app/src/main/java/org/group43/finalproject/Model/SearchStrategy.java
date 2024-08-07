package org.group43.finalproject.Model;

public interface SearchStrategy {
    boolean matchesInSearch(Artifact artifact, String lot, String name, String category, String period);
}

