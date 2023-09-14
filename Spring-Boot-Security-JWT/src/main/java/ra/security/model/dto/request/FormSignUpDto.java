package ra.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormSignUpDto {

    //    private String mail;
    @NotEmpty
    @Size(min = 6, message = "Username minimum 6 characters")
    private String username;

    @Size(min = 6, message = "Password minimum 6 characters")
    private String password;

    private List<String> roles;
}
