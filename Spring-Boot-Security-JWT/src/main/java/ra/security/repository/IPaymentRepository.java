package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.security.model.domain.Payment;

public interface IPaymentRepository extends JpaRepository<Payment,Long> {
}
