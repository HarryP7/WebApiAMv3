package ru.harry.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.harry.Entity.Location;
import ru.harry.Entity.Role;
import ru.harry.Entity.User;
import ru.harry.Repository.LocationRepository;
import ru.harry.Repository.UsersRepository;
import ru.harry.dto.UserLoginDTO;
import ru.harry.dto.UserRegistrDTO;
import ru.harry.exception.CustomException;
import ru.harry.security.JwtTokenProvider;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    public String signin(UserLoginDTO user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
            return jwtTokenProvider.createToken(userRepository.findByLogin(user.getLogin())/*, userRepository.findByLogin(user.getLogin()).getRole()*/);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid login/password entered", HttpStatus.BAD_REQUEST);
        }
    }

    public String signup(UserRegistrDTO user) {
        User userN = modelMapper.map(user, User.class);
        if (!EmailValidator.getInstance().isValid(user.getEmail()))
            throw new CustomException("Invalid email entered", HttpStatus.BAD_REQUEST);
        if (user.getPassword().length() < 8)
            throw new CustomException("Minimum password length: 8 characters", HttpStatus.BAD_REQUEST);
        if (userRepository.existsByLogin(user.getLogin()))
            throw new CustomException("Login is already in use", HttpStatus.BAD_REQUEST);
        if (user.getLogin().length() < 4)
            throw new CustomException("Minimum login length: 4 characters", HttpStatus.BAD_REQUEST);
        if(!userRepository.existsByEmail(user.getEmail())) {
            userN.setUid(UUID.randomUUID().toString());
            userN.setPassword(passwordEncoder.encode(user.getPassword()));
            userN.setRole(Role.ROLE_USER);
            userN.setCreateDate(new Timestamp(System.currentTimeMillis()));
            Location loc = new Location();
            loc.setCity(user.getCity());
            loc=locationRepository.save(loc);
            userN.setHome(loc);
            userRepository.save(userN);
            userN.setToken(jwtTokenProvider.createToken(userRepository.findByLogin(user.getLogin())/*, user.getRole()*/));
            return userN.getToken();
        } else {
            throw new CustomException("Email is already in use", HttpStatus.BAD_REQUEST);
        }
    }

  /*  public void delete(String login) {
        userRepository.deleteByLogin(login);
    }

    public User search(String username) {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User whoami(HttpServletRequest req) {
        return userRepository.findByLogin(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(req)));
    }

    /*public String refresh(String username) {
        return jwtTokenProvider.createToken(userRepository.findByLogin(username), userRepository.findByLogin(username).getRole());
    }*/
}
