package org.group43.finalproject.Model;

public class SearchParamsModel {
    private static String lot = "";
    private static String name = "";
    private static String category = "";
    private static String period = "";
    private static SearchStrategy searchStrategy = new BasicSearchStrategy();

    public SearchParamsModel() {}

    public static String getLot() {
        return lot;
    }

    public static String getName() {
        return name;
    }

    public static String getCategory() {
        return category;
    }

    public static String getPeriod() {
        return period;
    }

    public static void setLot(String lot) {
        SearchParamsModel.lot = lot;
    }

    public static void setName(String name) {
        SearchParamsModel.name = name;
    }

    public static void setCategory(String category) {
        SearchParamsModel.category = category;
    }

    public static void setPeriod(String period) {
        SearchParamsModel.period = period;
    }

    public static void setSearchStrategy(SearchStrategy searchStrategy) {
        SearchParamsModel.searchStrategy = searchStrategy;
    }

    public static boolean matchesInSearch(Artifact artifact, String lot, String name, String category, String period) {
        return searchStrategy.matchesInSearch(artifact, lot, name, category, period);
    }
}
