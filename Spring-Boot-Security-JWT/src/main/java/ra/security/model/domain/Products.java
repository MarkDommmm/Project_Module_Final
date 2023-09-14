package ra.security.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Long discount_id;

    private String name;

    private double price;

    private int stock;

    private String description;

    private String image;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date modified_at;


    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    @ManyToMany
    private Set<Category> category = new HashSet<>();

    @ManyToOne
    private Brand brand;




    private boolean status;
}
