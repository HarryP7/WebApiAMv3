package ru.harry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.harry.Entity.Event;
import ru.harry.Entity.ParticipationStatus;
import ru.harry.Entity.User;

import java.sql.Timestamp;

public class EventParticipantsDTO {
    private Long idUser;
    private User user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm")
    private Timestamp joinedAt;

    public Long getIdUser() {
        return user.getId();
    }
    public String getUser() { return user.getFullName(); }
    public String getImage() {
        return user.getImageUrl();
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Timestamp getJoinedAt() {
        return joinedAt;
    }
    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }
    public int getRating() {
        return user.getRating();
    }
}
