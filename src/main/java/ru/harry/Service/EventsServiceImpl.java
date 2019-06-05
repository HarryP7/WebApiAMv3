package ru.harry.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.harry.Entity.*;
import ru.harry.Repository.EventReviewRepository;
import ru.harry.Repository.EventsRepository;
import ru.harry.Repository.LocationRepository;
import ru.harry.Repository.ParticipationRepository;
import ru.harry.dto.EventDataDTO;
import ru.harry.dto.EventFilter;
import ru.harry.dto.EventListDTO;
import ru.harry.dto.EventReviewDTO;
import ru.harry.exception.CustomException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementations of {@link EventsService} interface.
 * @author HarryPC
 * @version 1.0
 */

@Service
public class EventsServiceImpl implements EventsService<Event> {

    final EventsRepository eventsRepo;
    @Autowired
    public EventsServiceImpl(EventsRepository eventsRepo) {
        this.eventsRepo = eventsRepo;
    }
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ParticipationRepository participationRepo;
    @Autowired
    private EventReviewRepository eventReviewRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Event getById(Long id) {
        return eventsRepo.findOne(id);
    }
    @Override
    public Event save(EventDataDTO event) {
        Event entity = modelMapper.map(event, Event.class);
        Location loc = new Location();
        loc.setCity(event.getCity());//обязательное поле
        loc.setStreet(event.getStreet());
        loc.setBuilding(event.getBuilding());
        loc.setLat(event.getLat());//обязательное поле
        loc.setLng(event.getLng());//обязательное поле
        locationRepository.save(loc);
        entity.setLocation(loc);
        entity.setUid(UUID.randomUUID().toString());
        entity.setDatePlace(new Timestamp(System.currentTimeMillis()));
        entity.setStatus(EventStatus.Create);
        return eventsRepo.save(entity);
    }
    @Override
    public Event update(EventDataDTO event, Long id) {
        Event entity = eventsRepo.findOne(id); //находим нужное событие
        Location loc = entity.getLocation();
        loc.setCity(event.getCity());
        loc.setStreet(event.getStreet());
        loc.setBuilding(event.getBuilding());
        loc.setLat(event.getLat());
        loc.setLng(event.getLng());
        entity.setLocation(loc);
        entity.setTitle(event.getTitle());
        entity.setImageUrl(event.getImageUrl());
        entity.setStartTime(event.getStartTime());
        entity.setEndTime(event.getEndTime());
        entity.setPlace(event.getPlace());
        entity.setCost(event.getCost());
        entity.setDescrip(event.getDescrip());
        return eventsRepo.save(entity);
    }
    @Override
    public void cancel(Long id) {
        Event entity = eventsRepo.findOne(id);
        entity.setStatus(EventStatus.Canceled);
        eventsRepo.save(entity);
    }
    @Override
    public void join(Event ev, User user) {
        Participation pr = new Participation();
        pr.setUser(user);
        pr.setEvent(ev);
        pr.setJoinedAt(new Timestamp(System.currentTimeMillis()));
        pr.setStatus(ParticipationStatus.Accepted);
        participationRepo.save(pr);
        if(ev.getStatus()!=EventStatus.Joined){
            ev.setStatus(EventStatus.Joined);
            eventsRepo.save(ev);
        }
    }
    @Override
    public void left(Event ev, User user) {
        Participation pr = participationRepo.findByUserAndEvent(user, ev);
        participationRepo.delete(pr);
    }
    @Override
    public EventReview review(Event ev, EventReviewDTO review, User user){
        for(Participation p : ev.getParticipants()) {
            if (p.getUser()==user){
                EventReview rev = new EventReview();
                rev.setEvent(ev);
                rev.setAuthor(user);
                rev.setComment(review.getComment());
                rev.setRating(review.getRating());
                rev.setDatePlace(new Timestamp(System.currentTimeMillis()));
                return eventReviewRepo.save(rev);
            }
        }
        return null;
    }
    @Override
    public EventReview reviewUp(Event ev, EventReviewDTO review, User user){
        EventReview rev = eventReviewRepo.findByAuthorAndEvent(user, ev);
        rev.setComment(review.getComment());
        rev.setRating(review.getRating());
        rev.setDatePlace(new Timestamp(System.currentTimeMillis()));
        return eventReviewRepo.save(rev);
    }
    @Override
    public List<EventListDTO> filter(EventFilter filter) {
        List<Event> events = eventsRepo.findAll();
        List<EventListDTO> eventsMap = new ArrayList<>();
        for(Event ev : events) {
            if (filter.getCity()!=null && ev.getLocation().getCity().contains(filter.getCity()))
                eventsMap.add(modelMapper.map(ev, EventListDTO.class));
        }
        if(eventsMap.isEmpty()){
            for(Event ev : events) {
                eventsMap.add(modelMapper.map(ev, EventListDTO.class));
            }
        }
        List<EventListDTO> eventsMap1 = new ArrayList<>();
        for(EventListDTO ev : eventsMap) {
            if (filter.getWithDate()!=null && filter.getToDate()!=null &&
                    ev.getStartTime().after(filter.getWithDate()) &&
                    ev.getStartTime().before(filter.getToDate()))
                eventsMap1.add(ev);
        }
        if(eventsMap1.isEmpty())
            eventsMap1.addAll(eventsMap);
        List<EventListDTO> eventsMap2 = new ArrayList<>();
        for(EventListDTO ev : eventsMap1) {
            if (filter.getTitle() != null && ev.getTitle().contains(filter.getTitle()))
                eventsMap2.add(ev);
        }
        if(eventsMap2.isEmpty())
            eventsMap2.addAll(eventsMap1);
        List<EventListDTO> eventsMap3 = new ArrayList<>();
        for(EventListDTO ev : eventsMap2) {
            if (filter.getLive()!=null && filter.getLive() && ev.getStatus()==EventStatus.Live)
                eventsMap3.add(ev);
        }
        if(eventsMap3.isEmpty())
           return eventsMap2;
        else return eventsMap3;
    }
    @Override
    public void delete(Long id) {
        eventsRepo.delete(id);
    }
}
