package ra.security.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Orders> orders;


    private int amount;
    private String provider;
    private boolean status;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date modified_at;


}
