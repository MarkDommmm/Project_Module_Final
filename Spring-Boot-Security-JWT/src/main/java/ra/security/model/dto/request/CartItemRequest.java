package ra.security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Product;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemRequest {


    private Long idOrder;

    private Product product;

    private int quantity;

    private double price;
    private boolean status;


}
