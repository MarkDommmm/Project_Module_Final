package ra.security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryRequest {

    @NotBlank(message = "Product name cannot be blank")
    @NotEmpty(message = "Product name cannot be empty!!!")
    private String name;
    private Set<Product> products = new HashSet<>();

}
