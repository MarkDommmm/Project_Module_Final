package ra.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.security.model.domain.Shipment;

public interface IShipmentRepository extends JpaRepository<Shipment,Long> {
    boolean existsByAddress(String address);
}
