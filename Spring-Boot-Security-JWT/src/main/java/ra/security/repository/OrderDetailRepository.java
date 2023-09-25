package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.security.model.domain.OrderDetails;
import ra.security.model.domain.Orders;
import ra.security.model.dto.response.OrderDetailsResponse;
import ra.security.model.dto.response.OrdersResponse;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetails,Long> {
List<OrderDetails> findByOrders (Orders orders);
}
