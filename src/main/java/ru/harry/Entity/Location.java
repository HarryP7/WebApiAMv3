package ru.harry.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @JsonIgnore
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "City")
    private String city;
    @Basic
    @Column(name = "Street")
    private String street;
    @Basic
    @Column(name = "Building")
    private String building;
    @Basic
    @Column(name = "Lat")
    private BigDecimal lat;
    @Basic
    @Column(name = "Lng")
    private BigDecimal lng;
    @JsonIgnore
    @OneToOne(mappedBy = "home")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @JsonIgnore
    @OneToOne(mappedBy = "location")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Event event;

    public Long getId() {
        return id;
    }
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
    public void setId(Long id) {
        this.id = id;
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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
}
