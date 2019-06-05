package ru.harry.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.harry.Entity.User;

import javax.transaction.Transactional;

/**
 * Repository interface for {@link User} class.
 *
 * @author HarryPC
 * @version 1.0
 */
public interface UsersRepository extends JpaRepository<User, Long> {
    boolean existsByLogin(String login);
    boolean existsByEmail(String login);
    User findByLogin(String login);
    User findById(long id);
    @Transactional
    void deleteById(Long id);
}
