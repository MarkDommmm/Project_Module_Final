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
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double discount_percent;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date modified_at;

//    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Users> users = new ArrayList<>();


}
