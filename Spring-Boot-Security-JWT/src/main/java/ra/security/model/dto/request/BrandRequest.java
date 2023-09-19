package ra.security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BrandRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "Product name cannot be blank")
    @NotEmpty(message = "Product name cannot be empty!!!")
    private String name;



//    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
//    private Set<Products> products = new HashSet<>();

}
