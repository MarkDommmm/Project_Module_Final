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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "discount_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id"))
    private List<Discount> discount;


    private String name;

    private double price;

    private int stock;

    private String description;


    private String main_image;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<ImageProduct> images;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;

    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    @ManyToMany
    private List<Category> category = new ArrayList<>();

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Color colors;

    private boolean status;
}
