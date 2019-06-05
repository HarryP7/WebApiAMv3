package ru.harry.REST;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.harry.Entity.*;
import ru.harry.Repository.EventsRepository;
import ru.harry.Repository.UsersRepository;
import ru.harry.Service.EventsService;
import ru.harry.Service.UsersService;
import ru.harry.dto.*;
import ru.harry.exception.CustomException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for {@link Event} connected requests.
 *
 * @author HarryPC
 * @version 1.0
 */

@RestController
@RequestMapping("/event")
public class EventsRestController {
    private static final Logger log = LoggerFactory.getLogger(EventsRestController.class);

    private final EventsService eventsService;
    private final UsersService usersService;

    @Autowired
    private UsersRepository userRepo;
    @Autowired
    private EventsRepository eventsRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public EventsRestController(EventsService eventsService, UsersService usersService) {
        this.eventsService = eventsService;
        this.usersService = usersService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List> getAllEvents(HttpServletRequest req) {
        User userC = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN EventsController {} gotAllEvents", userC.getLogin());
        List<Event> events = eventsRepo.findAll();
        if (events.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<EventListDTO> eventsMap = new ArrayList<>();
        for (Event l : events) {
            if (l.getStartTime().before(new Timestamp(System.currentTimeMillis())) &&
                    l.getEndTime().after(new Timestamp(System.currentTimeMillis())) && l.getStatus() != EventStatus.Live) {
                l.setStatus(EventStatus.Live);
                eventsRepo.save(l);
            }
            if (l.getEndTime().before(new Timestamp(System.currentTimeMillis())) && l.getStatus() != EventStatus.Completed) {
                l.setStatus(EventStatus.Completed);
                eventsRepo.save(l);
            }
            eventsMap.add(modelMapper.map(l, EventListDTO.class));
        }
        return new ResponseEntity<>(eventsMap, HttpStatus.OK);
    }

     @RequestMapping(value = "/filter", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
     @ResponseBody
     @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
     public ResponseEntity<List> getAllFilterEvents(@RequestBody @Valid EventFilter filter, HttpServletRequest req) {
         User userC = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
         log.info("IN EventsController {} gotAllFilterEvents", userC.getLogin());
         return new ResponseEntity<>(eventsService.filter(filter), HttpStatus.OK);
     }
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Event> getEvent(@PathVariable("id") Long id, HttpServletRequest req) {
        User userC = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Event entity = eventsService.getById(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("IN EventsController {} gotByIdEvent {}", userC.getLogin(), entity.getOrganizer().getFullName());
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Event> AddEvent(@RequestBody @Valid EventDataDTO event, HttpServletRequest req) {
        User user = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN EventsController {} AddedEvent \"{}\"", user.getLogin(), event.getTitle());
        HttpHeaders headers = new HttpHeaders();
        event.setOrganizer(user);
        return new ResponseEntity<>((Event) eventsService.save(event), headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") Long id, @RequestBody @Valid EventDataDTO event, HttpServletRequest req) {
        User user = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN EventsController {} updatedEvent \"{}\"", user.getLogin(), event.getTitle());
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>((Event) eventsService.update(event, id), headers, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/cancel")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<String> cancelEvent(@PathVariable("id") Long id, HttpServletRequest req) {
        User user = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        Event ev = eventsRepo.findById(id);
        log.info("IN EventsController {} canceledEvent \"{}\"", user.getLogin(), ev.getTitle());
        HttpHeaders headers = new HttpHeaders();
        eventsService.cancel(id);
        return new ResponseEntity<>("Event \"" + ev.getTitle() + "\" canceled successfully", headers, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/join")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<String> joinEvent(@PathVariable("id") Long id, HttpServletRequest req) {
        User user = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        Event ev = eventsRepo.findById(id);
        log.info("IN EventsController {} joinedToEvent \"{}\"", user.getLogin(), ev.getTitle());
        HttpHeaders headers = new HttpHeaders();
        eventsService.join(ev, user);
        return new ResponseEntity<>(user.getLogin()+" you have successfully joined the event \""+ev.getTitle()+"\"", headers, HttpStatus.OK);
    }
    @GetMapping(value = "{id}/left")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<String> leftEvent(@PathVariable("id") Long id, HttpServletRequest req) {
        User user = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        Event ev = eventsRepo.findById(id);
        log.info("IN EventsController {} leftEvent \"{}\"", user.getLogin(), ev.getTitle());
        HttpHeaders headers = new HttpHeaders();
        eventsService.left(ev, user);
        return new ResponseEntity<>(user.getLogin()+" you have successfully left the event \""+ev.getTitle()+"\"", headers, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/participants")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<EventParticipantsDTO>> participantsEvent(@PathVariable("id") Long id, HttpServletRequest req) {
        User user = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        Event ev = eventsRepo.findById(id);
        log.info("IN EventsController {} looksEventParticipants \"{}\"", user.getLogin(), ev.getTitle());
        HttpHeaders headers = new HttpHeaders();
        List<EventParticipantsDTO> ans = new ArrayList<>();
        for (Participation p : ev.getParticipants()) {
            ans.add(modelMapper.map(p, EventParticipantsDTO.class));
        }
        return new ResponseEntity<>(ans, headers, HttpStatus.OK);
    }
    @GetMapping(value = "{id}/reviews")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<EventReview>> getReviewsEvent(@PathVariable("id") Long id, HttpServletRequest req) {
        User user = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        Event ev = eventsRepo.findById(id);
        log.info("IN EventsController {} gotReviewsEvent \"{}\"", user.getLogin(), ev.getTitle());
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(ev.getEventReviews(), headers, HttpStatus.OK);
    }
    @PostMapping(value = "{id}/reviews")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<EventReview>> reviewsEvent(@PathVariable("id") Long id, @RequestBody @Valid EventReviewDTO review, HttpServletRequest req) {
        User user = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        Event ev = eventsRepo.findById(id);
        log.info("IN EventsController {} reviewedEvent \"{}\"", user.getLogin(), ev.getTitle());
        HttpHeaders headers = new HttpHeaders();
        if(eventsService.review(ev, review, user)==null)
            throw new CustomException(user.getLogin()+" you did not participate in the event", HttpStatus.BAD_REQUEST);
        ev = eventsRepo.findById(id);
        return new ResponseEntity<>(ev.getEventReviews(), headers, HttpStatus.CREATED);
    }
    @PutMapping(value = "{id}/reviews")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<EventReview> upReviewEvent(@PathVariable("id") Long id, @RequestBody @Valid EventReviewDTO review, HttpServletRequest req) {
        User user = userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        Event ev = eventsRepo.findById(id);
        log.info("IN EventsController {} UpdateReviewEvent \"{}\"", user.getLogin(), ev.getTitle());
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(eventsService.reviewUp(ev, review, user), headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Long id, HttpServletRequest req) {
        User userC = this.userRepo.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        Event events = this.eventsService.getById(id);
        if (events == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        log.info("IN EventsController {} deleteEvent \"{}\"", userC.getLogin(), events.getTitle());
        this.eventsService.delete(id);
        return new ResponseEntity<>("Event \"" + events.getTitle() + "\" deleted successfully", headers, HttpStatus.NO_CONTENT);
    }
}
