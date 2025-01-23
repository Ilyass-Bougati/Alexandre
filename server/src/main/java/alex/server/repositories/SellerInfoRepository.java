package alex.server.repositories;

import alex.server.entities.SellerInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerInfoRepository extends CrudRepository<SellerInfo, Long> {
}
