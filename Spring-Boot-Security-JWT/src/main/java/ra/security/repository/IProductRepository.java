package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Brand;
import ra.security.model.domain.Products;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Products, Long> {

}
