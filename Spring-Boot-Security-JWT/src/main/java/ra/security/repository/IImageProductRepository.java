package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.ImageProduct;

@Repository
public interface IImageProductRepository extends JpaRepository<ImageProduct, Long> {
    boolean existsByImage(String img);
}
