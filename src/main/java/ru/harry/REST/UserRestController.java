package ru.harry.REST;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.harry.Entity.Participation;
import ru.harry.Entity.User;
import ru.harry.Repository.UsersRepository;
import ru.harry.Service.UsersService;
import ru.harry.dto.EventListDTO;
import ru.harry.dto.EventParticipationDTO;
import ru.harry.dto.UserFilterDTO;
import ru.harry.dto.UserListDTO;
import ru.harry.exception.CustomException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for {@link User} connected requests.
 *
 * @author HarryPC
 * @version 1.0
 */

@RestController
@RequestMapping("/user")
public class UserRestController {
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UsersService usersService;
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    public UserRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List> getAllUsers(HttpServletRequest req) {
        User user = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN UsersController {} gotAllUser", user.getLogin());
        List<User> users = this.usersService.getAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<UserListDTO> usersMap = new ArrayList<>();
        for (User l : users) {
            usersMap.add(modelMapper.map(l, UserListDTO.class));
        }
        return new ResponseEntity<>(usersMap, HttpStatus.OK);
    }
    @PostMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List> getAllFilterUsers(HttpServletRequest req, @RequestBody @Valid UserFilterDTO search) {
        User user = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN UsersController {} getAllFilterUsers", user.getLogin());
        List<User> users = this.usersService.getAll();//.stream().filter(it -> it.getFullName().contains(search)).collect(Collectors.toList());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<UserListDTO> usersMap = new ArrayList<>();
        if(search.getFullName()!=null)
        for (User d : users) {
            if(d.getFullName() != null && d.getFullName().contains(search.getFullName()))
                usersMap.add(modelMapper.map(d, UserListDTO.class));
        }
        if (search.getCity() != null) {
            List<UserListDTO> usersC = new ArrayList<>();
            for (UserListDTO d : usersMap) {
                if (d.getHome().contains(search.getCity()))
                    usersC.add(d);
            }
            return new ResponseEntity<>(usersC, HttpStatus.OK);
        }
        if(!usersMap.isEmpty())
        return new ResponseEntity<>(usersMap, HttpStatus.OK);
        else return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/profile")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<User> getByIdUser(@PathVariable("id") Long id, HttpServletRequest req) {
        User user = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        if (id == null) {
            throw new CustomException("Введите id", HttpStatus.BAD_REQUEST);
        }
        User entity = this.usersService.getById(id);
        if (entity == null) {
            throw new CustomException("Пользователя с id "+id+" нет в бд",HttpStatus.NOT_FOUND);
        }
        log.info("IN UsersController {} gotProfileByIdUser {}", user.getLogin(), entity.getLogin());
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    //Получить события созданные пользователем
    @GetMapping(value = "{id}/events/own")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List> getUserEvent(@PathVariable("id") Long id, HttpServletRequest req) {
        User userC = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = this.usersService.getById(id);
        log.info("IN UsersController {} gotEventByIdUser {}", userC.getLogin(), user.getLogin());
        List<EventListDTO> eventsMap = new ArrayList<>();
        for (Object l : user.getEvents()) {
            eventsMap.add(modelMapper.map(l, EventListDTO.class));
        }
        return new ResponseEntity<>(eventsMap, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/events/future")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List> futureEvent(@PathVariable("id") Long id, HttpServletRequest req) {
        User userC = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        User user = this.userRepository.findById(id);
        log.info("IN UsersController {} gotFutureEventByIdUser {}", userC.getLogin(), user.getLogin());
        List<EventParticipationDTO> ans = new ArrayList<>();
        for (Participation pr : user.getParticipation()) {
            if (pr.getStatus().toString().equals("Accepted") && !pr.getEvent().getStatus().toString().equals("Canceled")
                    && pr.getEvent().getStartTime().after(new Timestamp(System.currentTimeMillis()))) {
                ans.add(modelMapper.map(pr, EventParticipationDTO.class));
            }
        }
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/events/history")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List> historyEvent(@PathVariable("id") Long id, HttpServletRequest req) {
        User userC = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        User user = this.userRepository.findById(id);
        log.info("IN UsersController {} gotHistoryEventByIdUser {}", userC.getLogin(), user.getLogin());
        List<EventParticipationDTO> ans = new ArrayList<>();
        for (Participation p : user.getParticipation()) {
            if (p.getStatus().toString().equals("Accepted") && !p.getEvent().getStatus().toString().equals("Canceled")
                    && p.getEvent().getStartTime().before(new Timestamp(System.currentTimeMillis()))) {
                ans.add(modelMapper.map(p, EventParticipationDTO.class));
            }
        }
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }
    /**!!!!!
     * @RequestMapping(value = "{id}/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> getUserGroup(@PathVariable("id") Long id) {
    log.info("IN UsersController getGroupByIdUser {}", id);
    if (id == null) {
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    User entity = (User) this.usersService.getById(id);

    if (entity == null) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(entity, HttpStatus.OK);
    }*/
}
