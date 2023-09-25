package ra.security.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class OrderDetailsResponse {

    private Long id;

    // Chỉ ánh xạ một số thuộc tính cần thiết từ OrderDetails
    private int quantity;
    private double price;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;



    // Sử dụng @JsonIgnore để loại bỏ mối quan hệ đệ quy
    @JsonIgnore
    private Orders orders;

    // Ánh xạ ID của sản phẩm
    private Long productId;
}

