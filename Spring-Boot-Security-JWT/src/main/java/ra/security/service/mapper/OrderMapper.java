package ra.security.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.security.model.domain.*;
import ra.security.model.dto.request.OrdersRequest;
import ra.security.model.dto.response.OrdersResponse;
import ra.security.repository.IDiscountRepsository;
import ra.security.repository.IShipmentRepository;
import ra.security.repository.IUserRepository;
import ra.security.service.IGenericMapper;

import java.util.Optional;

@Component
public class OrderMapper implements IGenericMapper<Orders, OrdersRequest, OrdersResponse> {
    @Autowired
    private IDiscountRepsository discountRepsository;
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
        String discount = null;
        if (orders.getDiscount() != null) {
            Optional<Discount> optionalDiscount = discountRepsository.findById(orders.getDiscount().getId());
            discount = optionalDiscount.map(dis -> String.valueOf(dis.getName())).orElse(null);
        }

        return OrdersResponse.builder()
                .id(orders.getId())
                .payment(orders.getPayment().getProvider())
                .discount(discount)
                .total_price(orders.getTotal_price())
                .shipment(orders.getShipment().getId())
                .order_at(orders.getOrder_at())
                .eDelivered(String.valueOf(orders.getEDelivered()))
                .users(orders.getUsers().getId())
                .status(orders.isStatus())
                .build();
    }




}
