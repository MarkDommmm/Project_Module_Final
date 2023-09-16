package ra.security.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Brand;
import ra.security.model.domain.Category;
import ra.security.model.domain.Color;
import ra.security.model.domain.OrderDetails;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private Long discount_id;

    private String name;

    private double price;

    private int stock;

    private String description;

    private String image;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;


     private List<OrderDetails> orderDetails;


    private List<String> category = new ArrayList<>();


    private Optional<Brand> brand;


    private List<String> colors;

    private boolean status;
}
