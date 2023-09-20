package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Category;
import ra.security.model.domain.Discount;

import java.util.List;

@Repository
public interface IDiscountRepsository extends JpaRepository<Discount, Long> {
    boolean existsByName(String discount);
    List<Discount> findAllByIdIn(List<Long> ids);
}
