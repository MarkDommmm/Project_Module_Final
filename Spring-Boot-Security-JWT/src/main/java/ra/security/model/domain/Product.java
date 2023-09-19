package ra.security.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Long discount_id;

    private String name;

    private double price;

    private int stock;

    private String description;


    private String main_image;

    @JsonIgnore
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "product" )
    private List<ImageProduct> images;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;




    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    @ManyToMany
    private List<Category> category = new ArrayList<>();

    @ManyToOne
    private Brand brand;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_color"
            , joinColumns = @JoinColumn(name = "color_id")
            , inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Color> colors;

    private boolean status;
}
