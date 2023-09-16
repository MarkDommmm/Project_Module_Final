package ra.security.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.dto.response.ProductResponse;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItem {

    private Long idCart;

    private Orders orders;

    private  ProductResponse product;

    private int quantity;

    private double price;
    private boolean status = true;


}
