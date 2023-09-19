package ra.security.service.mapper;

import org.springframework.stereotype.Component;
import ra.security.model.domain.Shipment;
import ra.security.model.dto.request.ShipmentRequest;
import ra.security.model.dto.response.ShipmentResponse;
import ra.security.service.IGenericMapper;

import java.util.Date;

@Component
public class ShipmentMapper implements IGenericMapper<Shipment, ShipmentRequest, ShipmentResponse> {
    @Override
    public Shipment toEntity(ShipmentRequest shipmentRequest) {
        return Shipment.builder()
                .address(shipmentRequest.getAddress())
                .email(shipmentRequest.getEmail())
                .phone(shipmentRequest.getPhone())
                .create_at(new Date())
                .orders(shipmentRequest.getOrders())
                .user(shipmentRequest.getUser())
                .build();
    }

    @Override
    public ShipmentResponse toResponse(Shipment shipment) {
        return ShipmentResponse.builder()
                .id(shipment.getId())
                .address(shipment.getAddress())
                .email(shipment.getEmail())
                .phone(shipment.getPhone())
                .create_at(shipment.getCreate_at())
                .user_id(shipment.getUser().getId())
                .build();
    }
}
