package ru.testexample;

public class Country {

    private String name;
    private String link;
    private int zoneCount;

    public Country(String name, String link, int zoneCount) {
        this.name = name;
        this.link = link;
        this.zoneCount = zoneCount;
    }

    public Country(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getZoneCount() {
        return zoneCount;
    }

    public void setZoneCount(int zoneCount) {
        this.zoneCount = zoneCount;
    }
}
