package alex.server.seller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerInfoRepository extends CrudRepository<SellerInfo, Long> {
}
