package ra.security.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product products;

    private int quantity;

    private double price;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date modified_at;

}
