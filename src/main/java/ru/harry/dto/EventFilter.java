package ru.harry.dto;

import java.sql.Timestamp;

public class EventFilter {
    private String Title;
    private Timestamp withDate;
    private Timestamp toDate;
    private String city;
    private Boolean live;

    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public Timestamp getWithDate() {
        return withDate;
    }
    public void setWithDate(Timestamp withDate) {
        this.withDate = withDate;
    }
    public Timestamp getToDate() {
        return toDate;
    }
    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Boolean getLive() {
        return live;
    }
    public void setLive(Boolean live) {
        this.live = live;
    }
}
