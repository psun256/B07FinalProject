package org.group43.finalproject.Model;

public class BasicSearchStrategy implements SearchStrategy {
    @Override
    public boolean matchesInSearch(Artifact artifact, String lot, String name, String category, String period) {
        return ((lot.isEmpty() || String.valueOf(artifact.getLotNumber()).equals(lot))
                && (name.isEmpty() || artifact.getName().toUpperCase().contains(name.toUpperCase()))
                && (category.isEmpty() || artifact.getCategory().equalsIgnoreCase(category))
                && (period.isEmpty() || artifact.getPeriod().equalsIgnoreCase(period)));
    }
}
