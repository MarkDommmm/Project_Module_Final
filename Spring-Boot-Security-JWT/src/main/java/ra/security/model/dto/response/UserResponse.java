package ra.security.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Orders;
import ra.security.model.domain.Role;
import ra.security.model.domain.Shipment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;

    private String username;

    private String password;

    private boolean status;

    private List<Shipment> shipments = new ArrayList<>();


    private Set<Role> roles = new HashSet<>();

    private List<Orders> orders = new ArrayList<>();
}
