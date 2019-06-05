package ru.harry.Service;

import ru.harry.Entity.Event;
import ru.harry.Entity.EventReview;
import ru.harry.Entity.User;
import ru.harry.dto.EventDataDTO;
import ru.harry.dto.EventFilter;
import ru.harry.dto.EventListDTO;
import ru.harry.dto.EventReviewDTO;

import java.util.List;
/**
 * Service interface for {Events} class.
 * @author HarryPC
 * @version 1.0
 */
public interface EventsService<T> {

    Event getById(Long id);
    T save(EventDataDTO event);
    T update(EventDataDTO event, Long id);
    void cancel(Long id);
    void delete(Long id);
    void join(T ev, User user);
    void left(T ev, User user);
    EventReview review(T ev, EventReviewDTO review, User user);
    EventReview reviewUp(T ev, EventReviewDTO review, User user);
    List<EventListDTO> filter(EventFilter filter);
}
