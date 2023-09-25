package ra.security.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.security.exception.CustomException;
import ra.security.model.domain.OrderDetails;
import ra.security.model.domain.Orders;
import ra.security.model.domain.Product;
import ra.security.model.dto.request.OrderDetailsRequest;
import ra.security.model.dto.response.OrderDetailsResponse;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IOrderRepository;
import ra.security.repository.IProductRepository;
import ra.security.service.IGenericMapper;
import ra.security.service.impl.ProductService;

import java.util.Optional;

@Component
public class OrderDetailsMapper implements IGenericMapper<OrderDetails, OrderDetailsRequest, OrderDetailsResponse> {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;

    @Override
    public OrderDetails toEntity(OrderDetailsRequest orderDetailsRequest) {

        return OrderDetails.builder()
                .orders(orderDetailsRequest.getOrders())
                .products(orderDetailsRequest.getProducts())
                .price(orderDetailsRequest.getPrice())
                .quantity(orderDetailsRequest.getQuantity())
                .created_at(orderDetailsRequest.getCreated_at())
                .build();
    }

    @Override
    public OrderDetailsResponse toResponse(OrderDetails orderDetails) throws CustomException {

        return OrderDetailsResponse.builder()
                .id(orderDetails.getId())
                .quantity(orderDetails.getQuantity())
                .productId(orderDetails.getProducts().getId())
                .price(orderDetails.getPrice())
                .orders(orderDetails.getOrders())
                .created_at(orderDetails.getCreated_at()).build();
    }

}
