package ru.harry.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.harry.Entity.Event;
import ru.harry.Entity.Participation;
import ru.harry.Entity.User;


/**
 * Repository interface for {@link Participation} class.
 *
 * @author HarryPC
 * @version 1.0
 */
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Participation findByUserAndEvent(User User, Event event);
}
