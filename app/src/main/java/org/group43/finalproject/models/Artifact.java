package org.group43.finalproject.models;

public class Artifact {

    private int lotNumber;
    private String name;
    private String category;
    private String period;
    private String description;
    //picture or video

    public Artifact() {
    }

    public Artifact(int lotNumber, String name, String category, String period, String description) {
        this.lotNumber = lotNumber;
        this.name = name;
        this.category = category;
        this.period = period;
        this.description = description;
    }

    public int getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }
}
