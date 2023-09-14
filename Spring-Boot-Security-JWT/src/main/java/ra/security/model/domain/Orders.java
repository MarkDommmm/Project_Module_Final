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
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Discount discount;

    private double total_price;

    //    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Shipment> shipments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Shipment shipment;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date order_at;

    private boolean status;

}
