package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.security.model.domain.OrderDetails;

public interface OrderDetailRepository extends JpaRepository<OrderDetails,Long> {
}
