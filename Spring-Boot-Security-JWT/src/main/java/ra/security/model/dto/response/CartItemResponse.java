package ra.security.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Products;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemResponse {

    private Long idCart;

    private Long idOrder;

    private Products product;

    private int quantity;

    private float price;


}