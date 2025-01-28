package alex.server.cart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartElementRepository extends CrudRepository<CartElement, Long> {
}
