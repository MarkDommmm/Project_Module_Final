package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.security.model.domain.Category;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIdIn(List<Long> ids);
}
