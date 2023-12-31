package ra.security.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "image_product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ImageProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	private String image;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
}
