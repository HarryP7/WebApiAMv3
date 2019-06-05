package ru.harry.Repository;


import ru.harry.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Event} class.
 *
 * @author HarryPC
 * @version 1.0
 */
public interface EventsRepository extends JpaRepository<Event, Long> {
    Event findById(long id);
}
