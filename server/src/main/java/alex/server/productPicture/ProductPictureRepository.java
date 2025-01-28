package alex.server.productPicture;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPictureRepository extends CrudRepository<ProductPicture, Long> {
}
