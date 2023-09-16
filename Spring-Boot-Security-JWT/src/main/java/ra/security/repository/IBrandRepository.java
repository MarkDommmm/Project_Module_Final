package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Brand;
@Repository
public interface IBrandRepository extends JpaRepository<Brand,Long> {
    boolean existsByName(String brandName);
}
