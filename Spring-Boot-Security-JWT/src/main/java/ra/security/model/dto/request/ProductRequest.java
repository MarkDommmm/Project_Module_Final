package ra.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private Long discount_id;

    private String name;

    private double price;

    private int stock;

    private String description;

    private List<MultipartFile> file;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;


    private List<Long> category;

    private Long brandId ;

    private List<Long> colors;

    private boolean status;
}
