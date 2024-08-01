package org.group43.finalproject.Model;

public class Artifact {

    private int lotNumber;
    private String name;
    private String category;
    private String period;
    private String description;
    private String file;
    private String fileType;

    public Artifact() {
    }

    public Artifact(int lotNumber, String name, String category, String period,
                    String description, String file, String fileType) {
        setLotNumber(lotNumber);
        setName(name);
        setCategory(category);
        setPeriod(period);
        setDescription(description);
        setFile(file);
        setFileType(fileType);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Artifact)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Artifact other = (Artifact) obj;
        return getLotNumber() == other.getLotNumber();
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(getLotNumber()).hashCode();
    }
}
