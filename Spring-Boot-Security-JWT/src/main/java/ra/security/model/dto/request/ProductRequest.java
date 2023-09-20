package ra.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ra.security.model.domain.Color;
import ra.security.model.domain.Discount;
import ra.security.security.validate.NoNullOrEmpty;

import javax.validation.constraints.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {



    @NotBlank(message = "Product name cannot be blank")
    @NotEmpty(message = "Product name cannot be empty!!!")
    @Size(min = 6, message = "Product name must be at least 6 characters")
    private String name;

    @NotNull(message = "Product price cannot be empty!!!")
    @Min(value = 1, message = "Product price must be greater than or equal to 1!!!")
    private Double price;

    @NotNull(message = "Product stock cannot be empty!!!")
    @Min(value = 1, message = "Product stock must be greater than or equal to 1!!!")
    private Integer stock;

    private String description;

    private List<MultipartFile> file;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created_at;
    @NotNull(message = "Brand cannot be empty!!!")
    private Long brandId;

    @NoNullOrEmpty(message = "Discount cannot be null or empty")
    private List<Long> discount;
    @NoNullOrEmpty(message = "Category cannot be null or empty")
    private List<Long> category;

    @NotNull(message = "Color cannot be empty!!!")
    private Long colors;
}
