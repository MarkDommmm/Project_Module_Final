package ra.security.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Orders;
import ra.security.model.domain.Product;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemResponse {

    private Long idCart;

    private ProductResponse product;

    private int quantity;

    private double price;
    private  boolean status = true;


}
