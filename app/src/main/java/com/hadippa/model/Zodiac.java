package com.hadippa.model;

/**
 * Created by Palak on 16-11-2016.
 */

public class Zodiac {

    private String zodiacName;
    private String startDate;
    private String endDate;

    public Zodiac(String zodiacName, String startDate, String endDate) {
        this.zodiacName = zodiacName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Zodiac{" +
                "zodiacName='" + zodiacName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public String getZodiacName() {
        return zodiacName;
    }

    public void setZodiacName(String zodiacName) {
        this.zodiacName = zodiacName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
