package ra.security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Orders;
import ra.security.model.domain.Users;
import ra.security.security.validate.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShipmentRequest {

    @Size(min = 10, message = "Address must have at least 10 characters")
    private String address;

    @Pattern(regexp = "\\d{10}", message = "Phone number must have exactly 10 digits")
    private String phone;

    @ValidEmail
    private String email;
    private Date create_at;
    private Users user;
    private List<Orders> orders = new ArrayList<>();
}
