package ra.security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ColorRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "Product name cannot be blank")
    @NotEmpty(message = "Product name cannot be empty!!!")
    private String name;

    @ManyToOne
    private Product products;

//    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
//    private Set<Products> products = new HashSet<>();

}
