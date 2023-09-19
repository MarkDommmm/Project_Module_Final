package ra.security.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.security.model.domain.EDelivered;
import ra.security.model.domain.Orders;
import ra.security.model.domain.Shipment;
import ra.security.model.domain.Users;
import ra.security.model.dto.request.OrdersRequest;
import ra.security.model.dto.response.OrdersResponse;
import ra.security.repository.IShipmentRepository;
import ra.security.repository.IUserRepository;
import ra.security.service.IGenericMapper;

import java.util.Optional;

@Component
public class OrderMapper implements IGenericMapper<Orders, OrdersRequest, OrdersResponse> {
    @Autowired
    private IShipmentRepository shipmentRepository;
    @Override
    public Orders toEntity(OrdersRequest ordersRequest) {
        return Orders.builder()
                .payment(ordersRequest.getPayment())
                .discount(ordersRequest.getDiscount())
                .total_price(ordersRequest.getTotal_price())
                .shipment(ordersRequest.getShipment())
                .order_at(ordersRequest.getOrder_at())
                .status(ordersRequest.isStatus())
                .build();
    }

    @Override
    public OrdersResponse toResponse(Orders orders) {
         return OrdersResponse.builder()
                .id(orders.getId())
                .payment(orders.getPayment().getId())
                .discount(orders.getDiscount())
                .total_price(orders.getTotal_price())
                .shipment(orders.getShipment().getId())
                .order_at(orders.getOrder_at())
                .eDelivered(String.valueOf(orders.getEDelivered()))
                .users(orders.getUsers().getId())
                .status(orders.isStatus()).build();
    }

    public EDelivered findEDeliveredByString(String delivery) {
        switch (delivery) {
            case "pending":
                return EDelivered.PENDING;
            case "prepare":
                return EDelivered.PREPARE;
            case "delivery":
                return EDelivered.DELIVERY;
            case "success":
                return EDelivered.SUCCESS;
            case "cancel":
                return EDelivered.CANCEL;
        }
        throw new RuntimeException("Error in incorrect order format");
    }
}
