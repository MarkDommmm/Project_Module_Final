package ra.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Orders;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DiscountRequest {

    @NotBlank(message = "Product name cannot be blank")
    @NotEmpty(message = "Product name cannot be empty!!!")
    private String name;
    private String description;
    @NotNull(message = "Discount stock cannot be empty!!!")
    @Min(value = 1, message = "Discount stock must be greater than or equal to 1!!!")
    private Integer stock;
    @NotNull(message = "Promotion  cannot be empty!!!")
    @Min(value = 1, message = "Promotion   must be greater than or equal to 1!!!")
    private Integer promotion;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    private List<Orders> orders = new ArrayList<>();
}
