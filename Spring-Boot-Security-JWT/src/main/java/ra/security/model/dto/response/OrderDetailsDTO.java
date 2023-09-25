package ra.security.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.OrderDetails;
import ra.security.model.domain.Orders;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDetailsDTO {
    private OrdersResponse orders;
    private List<OrderDetailsResponse> orderDetailsList;

}
