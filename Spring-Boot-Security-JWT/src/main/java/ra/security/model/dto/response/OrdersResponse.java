package ra.security.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrdersResponse {

    private Long id;

    private String payment;

    private String discount;
    private String eDelivered;
    private double total_price;

    private Long shipment;
    private Long users;
    private Date order_at;

    private boolean status;

}
