package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.*;
import ra.security.model.domain.*;
import ra.security.model.dto.request.OrdersRequest;
import ra.security.model.dto.response.CartItemResponse;
import ra.security.model.dto.response.OrdersResponse;
import ra.security.model.dto.response.ProductResponse;
import ra.security.model.dto.response.ShipmentResponse;
import ra.security.repository.*;
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
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private IShipmentRepository shipmentRepository;
    @Autowired
    private IPaymentRepository paymentRepository;

    @Override
    public List<OrdersResponse> findAll() {
        return orderRepository.findAll().stream()
                .map((o -> orderMapper.toResponse(o))).collect(Collectors.toList());
    }

    @Override
    public OrdersResponse findById(Long aLong) throws CustomException {
        Optional<Orders> orders = orderRepository.findById(aLong);
        return orders.map(o -> orderMapper.toResponse(o)).orElseThrow(() ->
                new CustomException("Order not found"));
    }

    @Override
    public OrdersResponse save(OrdersRequest ordersRequest) throws CustomException {
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

    public Orders findOrderById(Long id) throws CustomException {
        Optional<Orders> optionalOrders = orderRepository.findById(id);
        return optionalOrders.orElseThrow(() -> new CustomException("order not found"));
    }

    public OrdersResponse changeStatus(Long id) throws CustomException {
        Orders o = findOrderById(id);
        o.setStatus(!o.isStatus());
        return orderMapper.toResponse(orderRepository.save(o));
    }

    public OrdersResponse changeDelivery(EDelivered newDelivery, Long orderId) throws CustomException {
        Orders orders = findOrderById(orderId);
        EDelivered currentDelivery = orders.getEDelivered();
        System.out.println(newDelivery + "+++++++++++++++++++++++++++++++");
        if (newDelivery == currentDelivery) {
            throw new CustomException("You are already in the " + currentDelivery + " status");
        }

        switch (currentDelivery) {
            case PENDING:
                if (newDelivery == EDelivered.DELIVERY || newDelivery == EDelivered.SUCCESS) {
                    throw new CustomException("You are in the status of waiting for confirmation");
                }
                break;
            case PREPARE:
                if (newDelivery == EDelivered.PENDING || newDelivery == EDelivered.SUCCESS) {
                    throw new CustomException("You are in preparation mode");
                }
                break;
            case DELIVERY:
                if (newDelivery == EDelivered.PENDING || newDelivery == EDelivered.PREPARE) {
                    throw new CustomException("You are in the delivery status");
                }
                break;
            case SUCCESS:
                if (newDelivery == EDelivered.PENDING || newDelivery == EDelivered.PREPARE || newDelivery == EDelivered.DELIVERY) {
                    throw new CustomException("You are in the successful delivery status");
                }
                break;
            case CANCEL:
                if (newDelivery != EDelivered.CANCEL) {
                    throw new CustomException("You are in the order cancellation status");
                }
                break;
            default:
                throw new CustomException("Invalid delivery status");
        }

        orders.setEDelivered(newDelivery);
        return orderMapper.toResponse(orderRepository.save(orders));
    }


    public EDelivered findDeliveryByInput(String typeDelivery) throws CustomException {
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
                throw new CustomException("delivery not found");
        }
    }

    public Users findUserById(Long userId) throws CustomException {
        Optional<Users> u = userRepository.findById(userId);
        return u.orElseThrow(() -> new CustomException("User not found"));
    }

    public Product findProductById(Long productId) throws CustomException {
        Optional<Product> p = productRepository.findById(productId);
        return p.orElseThrow(() -> new CustomException("Product not found"));
    }

    public Shipment findShipmentById(Long shipmentID) throws CustomException {
        Optional<Shipment> p = shipmentRepository.findById(shipmentID);
        return p.orElseThrow(() -> new CustomException("Shipment not found"));
    }

    public Payment findPaymentById(Long paymentId) throws CustomException {
        Optional<Payment> p = paymentRepository.findById(paymentId);
        return p.orElseThrow(() -> new CustomException("Payment not found"));
    }

    public OrdersResponse order(Object user, Long shipmentId, Long paymentId) throws CustomException {
        Optional<Users> u = userRepository.findByUsername(String.valueOf(user));
        Shipment shipment = findShipmentById(shipmentId);
        List<ShipmentResponse> check =  shipmentService.findShipmentsByUser(user);
        if (check == null) {
            throw  new CustomException("Shipment not found");
        }
        Payment payment = findPaymentById(paymentId);
        List<CartItemResponse> cartItemList = cartService.findAll();
        if (cartItemList.isEmpty()) {
            throw new CustomException("Cart isEmpty!!!");
        }
        // Tạo một đơn hàng
        double totalPrice = cartItemList.stream()
                .mapToDouble(CartItemResponse::getPrice) // Chuyển đổi từ CartItemResponse thành giá tiền (double)
                .sum();

        Orders order = Orders.builder()
                .order_at(new Date())
                .eDelivered(EDelivered.PENDING)
                .users(u.get())
                .shipment(shipment)
                .payment(payment)
                .total_price(totalPrice)
                .status(true)
                .build();
        orderRepository.save(order);

        for (CartItemResponse p : cartItemList) {
            Product product = findProductById(p.getIdProduct());
            product.setStock(product.getStock() - p.getQuantity());
            productRepository.save(product);
            OrderDetails orderDetails = OrderDetails.builder()
                    .orders(order)
                    .created_at(new Date())
                    .quantity(p.getQuantity())
                    .products(product)
                    .price(p.getPrice())
                    .build();
            orderDetailRepository.save(orderDetails);
        }
        cartService.clearCartList();
        u.get().getOrders().add(order);
        userRepository.save(u.get());
        return orderMapper.toResponse(order);
    }


    public List<OrdersResponse> showOrders(Object user) {
        Optional<Users> users = userRepository.findByUsername(String.valueOf(user));
        List<OrdersResponse> orders = new ArrayList<>();
        for (OrdersResponse o : findAll()) {
            if (users.get().getId().equals(o.getUsers())) {
                orders.add(o);
            }
        }
        return orders;
    }
}
