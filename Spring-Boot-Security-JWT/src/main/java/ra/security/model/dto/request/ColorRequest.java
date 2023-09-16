package ra.security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Products;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ColorRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String name;

    @ManyToOne
    private Products products;

//    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
//    private Set<Products> products = new HashSet<>();

}
