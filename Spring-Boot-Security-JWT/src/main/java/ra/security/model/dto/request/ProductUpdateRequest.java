package ra.security.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ra.security.model.domain.Category;
import ra.security.model.domain.Color;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductUpdateRequest {

	private Long discount_id;

	private String name;

	private double price;

	private int stock;

	private String description;



	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date created_at;


	private List<Long> category;

	private Long brandId ;

	private List<Long> colors;

	private boolean status;
}
