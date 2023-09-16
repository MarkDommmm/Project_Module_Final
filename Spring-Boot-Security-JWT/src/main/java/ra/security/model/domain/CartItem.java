package ra.security.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.dto.response.ProductResponse;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItem {

    private Long idCart;

    private Long idOrder;

    private ProductResponse product;

    private int quantity;

    private double price;


}
