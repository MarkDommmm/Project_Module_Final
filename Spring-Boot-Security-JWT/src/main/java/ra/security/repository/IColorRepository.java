package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Category;
import ra.security.model.domain.Color;

import java.util.List;
@Repository
public interface IColorRepository extends JpaRepository<Color,Long> {
    List<Color> findAllByIdIn(List<Long> ids);
    boolean existsByName(String colorName);
}
