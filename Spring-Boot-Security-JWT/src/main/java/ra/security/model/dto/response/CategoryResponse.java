package ra.security.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.security.model.domain.Product;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryResponse {


    private Long id;


    private String name;


    private Set<Product> products = new HashSet<>();

}
