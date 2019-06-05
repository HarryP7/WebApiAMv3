package ru.harry.dto;

import ru.harry.Entity.Evaluation;

/**
 * Simple JavaBean domain object that represents Events.
 * @author HarryPC
 * @version 1.0
 */
public class EventReviewDTO {
    private String comment;
    private Evaluation rating;

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
}
