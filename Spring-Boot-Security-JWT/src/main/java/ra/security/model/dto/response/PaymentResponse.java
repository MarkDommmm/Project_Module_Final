package ra.security.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Orders;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentResponse {

    private Long id;
    private String provider;
    private boolean status;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;



}
