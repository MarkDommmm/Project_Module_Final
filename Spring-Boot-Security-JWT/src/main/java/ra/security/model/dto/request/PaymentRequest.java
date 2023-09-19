package ra.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Orders;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentRequest {


    private List<Orders> orders;

    private String provider;
    private boolean status =true;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;



}
