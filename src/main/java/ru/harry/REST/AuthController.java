package ru.harry.REST;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.harry.Entity.User;
import ru.harry.Service.UserService;
import ru.harry.dto.UserRegistrDTO;
import ru.harry.dto.UserLoginDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;


    @PostMapping("/signin")
    public String login(@RequestBody @Valid UserLoginDTO user) {
        log.info("IN AuthController User {} has logged in", user.getLogin());
        return userService.signin(user);
    }

    @PostMapping("/signup")//new
    public String signup( @RequestBody @Valid UserRegistrDTO user) {
        log.info("IN AuthController User {} has registered", user.getLogin());
        return userService.signup(user); //modelMapper.map(user, User.class)
    }
}
