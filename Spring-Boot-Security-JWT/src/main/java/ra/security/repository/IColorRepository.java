package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.security.model.domain.Category;
import ra.security.model.domain.Color;

import java.util.List;

public interface IColorRepository extends JpaRepository<Color,Long> {
    List<Color> findAllByIdIn(List<Long> ids);
}
