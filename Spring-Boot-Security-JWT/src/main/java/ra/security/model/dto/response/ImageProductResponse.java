package ra.security.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Product;

import javax.persistence.Table;


@Table(name = "image_product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ImageProductResponse {
	

	private Long id;

	@JsonIgnore
	private String image;
	

	private Product product;
	
}
