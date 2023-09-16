package ra.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Orders;
import ra.security.model.domain.Product;

import javax.persistence.*;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetailsRequest {

    private Orders orders;


    private Product products;

    private int quantity;

    private double price;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date modified_at;

}
