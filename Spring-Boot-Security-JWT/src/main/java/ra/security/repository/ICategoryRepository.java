package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Category;

import java.util.List;
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIdIn(List<Long> ids);

    boolean existsByName(String categoryName);
}
