package ra.security.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.*;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private List<String> discount;

    private String name;

    private double price;

    private int stock;

    private String description;

    private String main_image;
    private List<String> images;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;


     private List<OrderDetails> orderDetails;


    private List<String> category = new ArrayList<>();


    private Optional<Brand> brand;


    private  Optional<Color> colors;

    private boolean status;
}
