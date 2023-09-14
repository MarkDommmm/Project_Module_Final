package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Role;
import ra.security.model.domain.RoleName;

import java.util.Optional;
@Repository

public interface IRoleRepository extends JpaRepository<Role,Long> {
        // tìm kiếm theo Role name
    Optional<Role> findByRoleName(RoleName roleName);
}
