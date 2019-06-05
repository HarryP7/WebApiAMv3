package ru.harry.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.harry.Entity.Event;
import ru.harry.Entity.EventReview;
import ru.harry.Entity.User;

/**
 * Repository interface for {@link EventReview} class.
 *
 * @author HarryPC
 * @version 1.0
 */
public interface EventReviewRepository extends JpaRepository<EventReview, Long> {
    EventReview findByAuthorAndEvent(User user, Event event);
}
