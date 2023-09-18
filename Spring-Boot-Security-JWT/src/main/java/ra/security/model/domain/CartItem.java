package ra.security.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItem {

    private Long idCart;

    private Orders orders;

    private  Product product;

    private int quantity;

    private double price;
    private boolean status = true;


}
