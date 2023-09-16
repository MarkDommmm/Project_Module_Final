package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Orders;

@Repository
public interface IOrderRepository extends JpaRepository<Orders,Long> {

}
