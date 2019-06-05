package ru.harry.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "users", schema = "webapiam"/*, catalog = ""*/)
@Getter
@Setter
public class User {
    @Id
    @Column(name = "Id_User", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false, name = "Login", length = 50)
    private String login;
    @Basic @JsonIgnore
    @Column(name = "Uid", nullable = false, length = 100)
    private String uid;
    @Basic
    @Column(name = "Full_Name", nullable = false, length = 100)
    private String fullName;
    @Basic
    @Column(name = "Email", unique = true, nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "Phone", length = 16)
    private String phone;
    @Basic
    @JsonIgnore//@Transient
    @Size(min = 8, message = "Minimum password length: 8 characters")
    @Column(name = "Password", nullable = false)
    private String password;
    @Basic
    @Column(name = "Image_Url", length = 100)
    private String imageUrl;
    @Basic
    @Column(name = "About_Me", length = 100)
    private String aboutMe;
    @Basic
    @Column(name = "rating", nullable = false)
    private int rating;
    @Basic
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Home")
    private Location home;
    @Basic
    @Column(name = "Create_Date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm")
    private Timestamp createDate; //Дата регистрации
    @JsonIgnore
    @OneToMany(mappedBy = "organizer", fetch=LAZY)
    private List<Event> events;
    @Basic
    @Column(name = "Role", nullable = false)
    private Role role;
    @Basic
    @JsonIgnore
    @Column(name = "token", nullable = false)
    private String token;
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch=LAZY)
    private List<Participation> participation;
    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch=LAZY, cascade = CascadeType.ALL)
    private List<EventReview> eventReviews;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public Location getHome() {
        return home;
    }
    public void setHome(Location home) {
        this.home = home;
    }
    public Timestamp getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    public List<Event> getEvents() {
        return events;
    }
    public void setEvents(List<Event> events) {
        this.events = events;
    }
    public List<Participation> getParticipation() {
        return participation;
    }
    public void setParticipation(List<Participation> participation) {
        this.participation = participation;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public List<EventReview> getEventReviews() {
        return eventReviews;
    }
    public void setEventReviews(List<EventReview> eventReviews) {
        this.eventReviews = eventReviews;
    }
}
