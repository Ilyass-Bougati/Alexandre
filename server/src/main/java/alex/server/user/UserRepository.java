package alex.server.user;

import alex.server.role.Role;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findDistinctByEmail(String email);
    Optional<User> findDistinctById(long id);

    boolean existsByEmail(@Email String email);

    @Query("select r.roles from User r where r.id = ?1")
    Optional<List<Role>> findRolesById(long userId);
}
