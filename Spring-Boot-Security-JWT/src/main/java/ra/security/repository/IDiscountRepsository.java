package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Discount;

@Repository
public interface IDiscountRepsository extends JpaRepository<Discount, Long> {
    boolean existsByName(String discount);
}
