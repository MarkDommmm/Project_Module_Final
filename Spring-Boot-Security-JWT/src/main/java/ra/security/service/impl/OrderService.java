package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.ColorException;
import ra.security.exception.OrderException;
import ra.security.exception.ProductException;
import ra.security.exception.UserException;
import ra.security.model.domain.*;
import ra.security.model.dto.request.OrdersRequest;
import ra.security.model.dto.response.CartItemResponse;
import ra.security.model.dto.response.OrdersResponse;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IOrderRepository;
import ra.security.repository.IProductRepository;
import ra.security.repository.IUserRepository;
import ra.security.repository.OrderDetailRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.OrderDetailsMapper;
import ra.security.service.mapper.OrderMapper;
import ra.security.service.mapper.ProductMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService implements IGenericService<OrdersResponse, OrdersRequest, Long> {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailsMapper orderDetailsMapper;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<OrdersResponse> findAll() {
        return orderRepository.findAll().stream()
                .map((o -> orderMapper.toResponse(o))).collect(Collectors.toList());
    }

    @Override
    public OrdersResponse findById(Long aLong) throws OrderException {
        Optional<Orders> orders = orderRepository.findById(aLong);
        return orders.map(o -> orderMapper.toResponse(o)).orElseThrow(() ->
                new OrderException("Order not found"));
    }

    @Override
    public OrdersResponse save(OrdersRequest ordersRequest) throws ColorException {
        return orderMapper.toResponse(orderRepository.save(orderMapper.toEntity(ordersRequest)));
    }

    @Override
    public OrdersResponse update(OrdersRequest ordersRequest, Long id) {
        Orders o = orderMapper.toEntity(ordersRequest);
        o.setId(id);
        return orderMapper.toResponse(orderRepository.save(o));
    }

    @Override
    public OrdersResponse delete(Long aLong) {
        return null;
    }

    public Orders findOrderById(Long id) throws OrderException {
        Optional<Orders> optionalOrders = orderRepository.findById(id);
        return optionalOrders.orElseThrow(() -> new OrderException("order not found"));
    }

    public OrdersResponse changeStatus(Long id) throws OrderException {
        Orders o = findOrderById(id);
        o.setStatus(!o.isStatus());
        return orderMapper.toResponse(orderRepository.save(o));
    }

    public OrdersResponse changeDelivery(String typeDelivery, Long id) throws OrderException {
        EDelivered type = findDeliveryByInput(typeDelivery);
        Orders orders = findOrderById(id);
        if (orders.getEDelivered().toString().equals("PENDING")) {
            if (type.toString().equals("DELIVERY") || type.toString().equals("SUCCESS")) {
                throw new OrderException("You are in the status of waiting for confirmation");
            }
            orders.setEDelivered(type);
        } else if (orders.getEDelivered().toString().equals("PREPARE")) {
            if (type.toString().equals("PENDING") || type.toString().equals("SUCCESS")) {
                throw new OrderException("You are in preparation mode");
            }
            orders.setEDelivered(type);
        } else if (orders.getEDelivered().toString().equals("DELIVERY")) {
            if (type.toString().equals("PENDING") || type.toString().equals("PREPARE")) {
                throw new OrderException("You are in the delivery status");
            }
            orders.setEDelivered(type);
        } else if (orders.getEDelivered().toString().equals("SUCCESS")) {
            if (type.toString().equals("PENDING") || type.toString().equals("PREPARE") || type.toString().equals("DELIVERY")) {
                throw new OrderException("You are in the successful delivery status");
            }
            orders.setEDelivered(type);
        } else {
            if (type.toString().equals("PENDING") || type.toString().equals("PREPARE") || type.toString().equals("DELIVERY") || type.toString().equals("SUCCESS")) {
                throw new OrderException("You are in the order cancellation status");
            }
            orders.setEDelivered(type);
        }
        return orderMapper.toResponse(orderRepository.save(orders));
    }

    public EDelivered findDeliveryByInput(String typeDelivery) throws OrderException {
        switch (typeDelivery) {
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
            default:
                throw new OrderException("delivery not found");
        }
    }

    public Users findUserById(Long userId) throws UserException {
        Optional<Users> u = userRepository.findById(userId);
        return u.orElseThrow(() -> new UserException("User not found"));
    }

    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> p = productRepository.findById(productId);
        return p.orElseThrow(() -> new ProductException("Product not found"));
    }

    public OrdersResponse order(Long userId) throws UserException, ProductException, OrderException {
        Users u = findUserById(userId);
        List<CartItemResponse> cartItemList = cartService.findAll();
        // Tạo một đơn hàng
        Orders order = Orders.builder()
                .order_at(new Date())
                .eDelivered(EDelivered.PENDING)
                .users(u)
                .status(true)
                .build();
        orderRepository.save(order);
        for (CartItemResponse p : cartItemList) {
            Product product = findProductById(p.getProduct().getId());
            OrderDetails orderDetails = OrderDetails.builder()
                    .orders(order)
                    .created_at(new Date())
                    .quantity(p.getQuantity())
                    .products(product)
                    .price(p.getPrice())
                    .build();
            orderDetailRepository.save(orderDetails);
        }
        u.getOrders().add(order);
        userRepository.save(u);
        return orderMapper.toResponse(order);
    }


}
