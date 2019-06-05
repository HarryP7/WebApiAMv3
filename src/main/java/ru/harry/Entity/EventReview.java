package ru.harry.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.harry.dto.UserShortDTO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Simple JavaBean domain object that represents Events.
 * @author HarryPC
 * @version 1.0
 */
@Entity
@Table(name = "eventreview", schema = "webapiam"/*, catalog = ""*/)
public class EventReview {
    @Id @JsonIgnore
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn (name = "Author", nullable = false)
    private User author;
    @Basic
    @ManyToOne @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Event", nullable = false)
    private Event event;
    @Basic
    @Column(name = "Comment", length = 250, nullable = false)
    private String comment;
    @Basic
    @Column(name = "Rating", length = 100)
    private Evaluation rating;
    @Basic
    @Column(name = "Date_Place", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm")
    private Timestamp datePlace;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public UserShortDTO getAuthor() {
        UserShortDTO user = new UserShortDTO();
        user.setFullName(author.getFullName());
        user.setImageUrl(author.getImageUrl());
        user.setId(author.getId());
        return user;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Evaluation getRating() {
        return rating;
    }
    public void setRating(Evaluation rating) {
        this.rating = rating;
    }
    public Timestamp getDatePlace() {
        return datePlace;
    }
    public void setDatePlace(Timestamp datePlace) {
        this.datePlace = datePlace;
    }
}
