package ru.harry.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.harry.Entity.Location;
import ru.harry.Entity.User;
import ru.harry.Repository.UsersRepository;
import ru.harry.dto.UserProfileUpDTO;
import ru.harry.exception.CustomException;
import ru.harry.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Implementations of {@link UsersService} interface.
 * @author HarryPC
 * @version 1.0
 */

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepo;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public List<User> getAll() {
        return usersRepo.findAll();
    }
    @Override
    public User getById(Long id) {
        return usersRepo.findOne(id);
    }
    @Override
    public User save(User user, UserProfileUpDTO userUp) {
        user.setLogin(userUp.getLogin());
        user.setFullName(userUp.getFullName());
        user.setEmail(userUp.getEmail());
        user.setPhone(userUp.getPhone());
        user.setImageUrl(userUp.getImageUrl());
        user.setAboutMe(userUp.getAboutMe());
        Location loc = user.getHome();
        loc.setCity(userUp.getCity());
        loc.setStreet(userUp.getStreet());
        loc.setBuilding(userUp.getBuilding());
        loc.setLat(userUp.getLat());
        loc.setLng(userUp.getLng());
        user.setHome(loc);
        return usersRepo.save(user);
    }
    @Override
    public void delete(Long id) {
        usersRepo.delete(id);
    }

    public User search(String login) {
        User user = usersRepo.findByLogin(login);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User whoami(HttpServletRequest req) {
        return usersRepo.findByLogin(jwtTokenProvider.getLogin(jwtTokenProvider.resolveToken(req)));
    }
}
