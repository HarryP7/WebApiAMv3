package ru.harry.REST;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.harry.Entity.Participation;
import ru.harry.Entity.User;
import ru.harry.Repository.UsersRepository;
import ru.harry.Service.UsersService;
import ru.harry.dto.EventListDTO;
import ru.harry.dto.EventParticipationDTO;
import ru.harry.dto.UserPassDTO;
import ru.harry.dto.UserProfileUpDTO;
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
@RequestMapping("/my")
public class MyRestController {
    private static final Logger log = LoggerFactory.getLogger(MyRestController.class);

    private final UsersService usersService;
    @Autowired
    public MyRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/profile")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<User> getByIdUser(HttpServletRequest req) {
        User user = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN MyController {} gotYourProfile", user.getLogin());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/profile")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<User> updateUser(HttpServletRequest req, @RequestBody @Valid UserProfileUpDTO userUp) {
        HttpHeaders headers = new HttpHeaders();
        if (userUp == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User user = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN MyController {} updatedYourProfile", userUp.getLogin());
        return new ResponseEntity<>(usersService.save(user,userUp), headers, HttpStatus.OK);
    }

    @PutMapping(value = "/password")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<String> passwordUser(HttpServletRequest req, @RequestBody @Valid UserPassDTO pass) {
        HttpHeaders headers = new HttpHeaders();
        if (pass.getPassword().isEmpty() || pass.getConfirmPassword().isEmpty())
            throw new CustomException("Password is Empty", HttpStatus.BAD_REQUEST);
        if (pass.getPassword().length() < 8)
            throw new CustomException("Minimum password length: 8 characters", HttpStatus.BAD_REQUEST);
        if (!pass.getPassword().equals(pass.getConfirmPassword()))
            throw new CustomException("Passwords don't match", HttpStatus.BAD_REQUEST);
        User user = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN MyController {} updatedYourPassword", user.getLogin());
        user.setPassword(passwordEncoder.encode(pass.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Password successfully updated " + user.getLogin(), headers, HttpStatus.OK);
    }

    //Получить свои созданные события
    @GetMapping(value = "/events/own")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List> getUserEvent(HttpServletRequest req) {
        User user = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN MyController {} gotYourCreatedEvents", user.getLogin());
        List<EventListDTO> eventsMap = new ArrayList<>();
        for(Object l : user.getEvents()) {
            eventsMap.add(modelMapper.map(l, EventListDTO.class));
        }
        return new ResponseEntity<>(eventsMap, HttpStatus.OK);
    }

    @GetMapping(value = "/events/future")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List> futureEvent(HttpServletRequest req) {
        User user = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN MyController {} gotFutureEvent", user.getLogin());
        List<EventParticipationDTO> ans = new ArrayList<>();
        for (Participation pr : user.getParticipation()) {
            if (pr.getStatus().toString().equals("Accepted") && !pr.getEvent().getStatus().toString().equals("Canceled")
                    && pr.getEvent().getStartTime().after(new Timestamp(System.currentTimeMillis()))) {
                ans.add(modelMapper.map(pr, EventParticipationDTO.class));
            }
        }
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    @GetMapping(value = "/events/history")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List> historyEvent(HttpServletRequest req) {
        User user = this.userRepository.findById(modelMapper.map(usersService.whoami(req), User.class).getId());
        log.info("IN MyController User {} got your History Event", user.getLogin());
        List<EventParticipationDTO> ans = new ArrayList<>();
        for (Participation p : user.getParticipation()) {
            if (p.getStatus().toString().equals("Accepted") && !p.getEvent().getStatus().toString().equals("Canceled")
                    && p.getEvent().getStartTime().before(new Timestamp(System.currentTimeMillis()))) {
                ans.add(modelMapper.map(p, EventParticipationDTO.class));
            }
        }
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }
    @DeleteMapping(value = "/profile")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteUser(HttpServletRequest req) {
        String login = modelMapper.map(usersService.whoami(req), User.class).getLogin();
        log.info("IN MyController deleteUser {}", login);
        usersService.delete(modelMapper.map(usersService.whoami(req), User.class).getId());
        return new ResponseEntity<>("Profile "+login+" successfully deleted ", HttpStatus.NO_CONTENT);
    }

    /**
     * !!!!!
     *
     * @RequestMapping(value = "{id}/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
     * public ResponseEntity<User> getUserGroup(@PathVariable("id") Long id) {
     * log.info("IN UsersController getGroupByIdUser {}", id);
     * if (id == null) {
     * return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     * }
     * User entity = (User) this.usersService.getById(id);
     * <p>
     * if (entity == null) {
     * return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     * }
     * return new ResponseEntity<>(entity, HttpStatus.OK);
     * }
     */
}
