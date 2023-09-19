package ra.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.security.validate.NoNullOrEmpty;
import ra.security.security.validate.ValidEmail;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormSignUpDto {

    @ValidEmail
    private String email;

    @NotBlank(message = "Product name cannot be blank")
    @NotEmpty(message = "Product name cannot be empty!!!")
    @Size(min = 6, message = "Product name must be at least 6 characters")
    private String username;

    @NotBlank(message = "Password name cannot be blank")
    @NotEmpty(message = "Password name cannot be empty!!!")
    @Size(min = 6, message = "Password name must be at least 6 characters")
    private String password;

    private List<String> roles;
}
