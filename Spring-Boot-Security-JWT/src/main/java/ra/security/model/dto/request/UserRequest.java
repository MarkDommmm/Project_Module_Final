package ra.security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Orders;
import ra.security.model.domain.Role;
import ra.security.model.domain.Shipment;
import ra.security.security.validate.NoNullOrEmpty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NoNullOrEmpty(message = "FullName cannot be null or empty")
    @NotEmpty
    @Size(min = 6, message = "FullName minimum 6 characters")
    private String name;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email invalidate")
    private String email;

    @NoNullOrEmpty(message = "Username cannot be null or empty")
    @NotEmpty
    @Size(min = 6, message = "Username minimum 6 characters")
    private String username;
    @NoNullOrEmpty(message = "Password cannot be null or empty")
    @Size(min = 6, message = "Password minimum 6 characters")
    private String password;

    private boolean status;

    private List<Shipment> shipments = new ArrayList<>();


    private Set<Role> roles = new HashSet<>();

    private List<Orders> orders = new ArrayList<>();
}
