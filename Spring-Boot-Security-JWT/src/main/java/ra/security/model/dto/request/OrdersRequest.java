package ra.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Discount;
import ra.security.model.domain.Payment;
import ra.security.model.domain.Shipment;

import javax.persistence.*;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrdersRequest {

    private Payment payment;

    private Discount discount;

    private double total_price;

    private String eDelivered;

    private Shipment shipment;

    private Date order_at;

    private boolean status;

}
