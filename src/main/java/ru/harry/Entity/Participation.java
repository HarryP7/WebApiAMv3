package ru.harry.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Simple JavaBean domain object that represents Events.
 * @author HarryPC
 * @version 1.0
 */
@Entity
@Table(name = "participation")
public class Participation {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "Status", length = 100)
    private ParticipationStatus status;
    @Basic
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn (name = "User", nullable = false)
    private User user;
    @Basic
    @Column(name = "Joined_At", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm")
    private Timestamp joinedAt;
    @Basic
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Event", nullable = false)
    private Event event;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public ParticipationStatus getStatus() {
        return status;
    }
    public void setStatus(ParticipationStatus status) {
        this.status = status;
    }
    public Timestamp getJoinedAt() {
        return joinedAt;
    }
    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
}
