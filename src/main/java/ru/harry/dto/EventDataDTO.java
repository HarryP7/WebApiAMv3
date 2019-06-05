package ru.harry.dto;

import ru.harry.Entity.EventStatus;
import ru.harry.Entity.Location;
import ru.harry.Entity.User;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class EventDataDTO {
    private String city;
    private String street;
    private String building;
    private BigDecimal lat;
    private BigDecimal lng;
    private String title;
    private String imageUrl;
    private Timestamp startTime;
    private Timestamp endTime;
    private String place;
    private BigDecimal cost;
    private User organizer;
    private String descrip;
    private EventStatus status;

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getBuilding() {
        return building;
    }
    public void setBuilding(String building) {
        this.building = building;
    }
    public User getOrganizer() {
        return organizer;
    }
    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }
    public BigDecimal getLat() {
        return lat;
    }
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }
    public BigDecimal getLng() {
        return lng;
    }
    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    public String getDescrip() {
        return descrip;
    }
    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
    public EventStatus getStatus() {
        return status;
    }
    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
