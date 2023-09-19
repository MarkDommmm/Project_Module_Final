package ra.security.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String address;

    private String phone;

    private String email;

    private Date create_at;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    private Orders orders;
    @JsonIgnore
    @OneToMany(mappedBy = "shipment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Orders> orders = new ArrayList<>();
}
