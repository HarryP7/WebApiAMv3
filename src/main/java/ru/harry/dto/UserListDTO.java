package ru.harry.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.harry.Entity.Event;
import ru.harry.Entity.Location;

import javax.persistence.OneToMany;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

public class UserListDTO {
    private Long id;
    private String imageUrl;
    private String fullName;
    private String aboutMe;
    private Location home;
    private int rating;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHome() {
        return home.getCity();
    }
    public void setHome(Location home) {
        this.home = home;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getAboutMe() {
        return aboutMe;
    }
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
}
