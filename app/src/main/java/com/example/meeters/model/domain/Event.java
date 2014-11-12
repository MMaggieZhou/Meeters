package com.example.meeters.model.domain;

import java.util.Date;

/**
 * Created by Ying on 11/11/14.
 */
public class Event {
    private String name;
    private String theme;
    private Date startDate;
    private Date endDate;
    private String location;
    private String description;
    private String availableSlots;

    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailableSlots(String availableSlots) {
        this.availableSlots = availableSlots;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getAvailableSlots() {
        return availableSlots;
    }
}
