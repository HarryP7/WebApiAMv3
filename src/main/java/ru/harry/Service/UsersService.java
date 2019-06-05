package ru.harry.Service;
import ru.harry.Entity.User;
import ru.harry.dto.UserProfileUpDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * Service interface for {Users} class.
 * @author HarryPC
 * @version 1.0
 */
public interface UsersService {
    List<User> getAll();
    User getById(Long id);
    User save(User user, UserProfileUpDTO userUp);
    void delete(Long id);
    User search(String login);
    User whoami(HttpServletRequest req);
}
