package ru.harry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.harry.Entity.Event;
import ru.harry.Entity.ParticipationStatus;

import java.sql.Timestamp;

public class EventParticipationDTO {
    private Long idEvent;
    private Event event;
    private Timestamp startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm")
    private Timestamp joinedAt;
    private ParticipationStatus status;

    public Long getIdEvent() {
        return event.getId();
    }
    public String getEvent() {
        return event.getTitle();
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, dd MMM yyyy HH:mm")
    public Timestamp getStartTime() {
        return event.getStartTime();
    }
    public String getImage() {
        return event.getImageUrl();
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    public Timestamp getJoinedAt() {
        return joinedAt;
    }
    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }
    public ParticipationStatus getStatus() {
        return status;
    }
    public void setStatus(ParticipationStatus status) {
        this.status = status;
    }
}
