package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.security.model.domain.Users;

import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository<Users,Long> {
    boolean existsByUsername(String username);
    Optional<Users> findByUsername(String username);
}
