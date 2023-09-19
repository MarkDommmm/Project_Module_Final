package ra.security.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Orders;
import ra.security.model.domain.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShipmentResponse {

    private Long id;
    private String address;
    private String phone;
    private String email;
    private Date create_at;
    @JsonIgnore
    private Long user_id;

}
