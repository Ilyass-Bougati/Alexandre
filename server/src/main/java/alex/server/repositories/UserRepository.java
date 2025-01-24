package alex.server.repositories;

import alex.server.entities.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findDistinctByEmail(String email);
    Optional<User> findDistinctById(long id);

    boolean existsByEmail(@Email String email);
}
