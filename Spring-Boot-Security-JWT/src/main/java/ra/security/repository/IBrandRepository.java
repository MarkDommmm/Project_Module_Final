package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.security.model.domain.Brand;

public interface IBrandRepository extends JpaRepository<Brand,Long> {
}
