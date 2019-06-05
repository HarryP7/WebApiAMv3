package ru.harry.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.harry.Entity.Location;


/**
 * Repository interface for {@link Location} class.
 *
 * @author HarryPC
 * @version 1.0
 */
public interface LocationRepository extends JpaRepository<Location, Long> {
}
