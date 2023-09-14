package ra.security.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Products products;

//    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
//    private Set<Products> products = new HashSet<>();

}
