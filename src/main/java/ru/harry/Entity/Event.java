package ru.harry.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import ru.harry.dto.UserShortDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

/**
 * Simple JavaBean domain object that represents Events.
 * @author HarryPC
 * @version 1.0
 */
@Entity
@Table(name = "events", schema = "webapiam"/*, catalog = ""*/)
public class Event {
    @Id
    @Column(name = "Id_Event", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic @JsonIgnore
    @Column(name = "Uid", nullable = false, length = 100)
    private String uid;
    @Basic
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Location", nullable = false)
    private Location location;
    @Basic
    @Column(name = "Title", nullable = false, length = 100)
    private String title;
    @Basic
    @Column(name = "Date_Place", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm")
    private Timestamp datePlace; //Дата размещения
    @Basic
    @Column(name = "Image_Url", length = 100)
    private String imageUrl;
    @Basic
    @Column(name = "Start_Time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm")
    private Timestamp startTime;
    @Basic
    @Column(name = "End_Time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm")
    private Timestamp endTime;
    @Basic
    @Column(name = "Place", length = 100)
    private String place;
    @Basic
    @Column(name = "Cost", nullable = false, precision = 2)
    private BigDecimal cost;
    @Basic
    @Column(name = "Description", length = 100)
    private String descrip;
    @Basic
    @Column(name = "Status", nullable = false)
    private EventStatus status;
    @Basic
    @ManyToOne
    @JoinColumn(name = "Organizer", nullable = false)
    private User organizer;
    @JsonIgnore
    @OneToMany(mappedBy = "event", fetch=LAZY, cascade = CascadeType.ALL)
    private List<Participation> participants;
    @JsonIgnore
    @OneToMany(mappedBy = "event", fetch=LAZY, cascade = CascadeType.ALL)
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
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Timestamp getDatePlace() {
        return datePlace;
    }
    public void setDatePlace(Timestamp datePlace) {
        this.datePlace = datePlace;
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
    public UserShortDTO getOrganizer() {
        UserShortDTO user = new UserShortDTO();
        user.setId(organizer.getId());
        user.setFullName(organizer.getFullName());
        user.setImageUrl(organizer.getImageUrl());
        return user;
    }
    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }
    public List<Participation> getParticipants() {
        return participants;
    }
    public void setParticipants(List<Participation> participants) {
        this.participants = participants;
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
    public List<EventReview> getEventReviews() {
        return eventReviews;
    }
    public void setEventReviews(List<EventReview> eventReviews) {
        this.eventReviews = eventReviews;
    }
}
