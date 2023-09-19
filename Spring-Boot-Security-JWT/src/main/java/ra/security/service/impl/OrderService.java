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

    public OrdersResponse changeDelivery(EDelivered newDelivery, Long orderId) throws OrderException {
        Orders orders = findOrderById(orderId);
        EDelivered currentDelivery = orders.getEDelivered();
        System.out.println(newDelivery + "+++++++++++++++++++++++++++++++");
        if (newDelivery == currentDelivery) {
            throw new OrderException("You are already in the " + currentDelivery + " status");
        }

        switch (currentDelivery) {
            case PENDING:
                if (newDelivery == EDelivered.DELIVERY || newDelivery == EDelivered.SUCCESS) {
                    throw new OrderException("You are in the status of waiting for confirmation");
                }
                break;
            case PREPARE:
                if (newDelivery == EDelivered.PENDING || newDelivery == EDelivered.SUCCESS) {
                    throw new OrderException("You are in preparation mode");
                }
                break;
            case DELIVERY:
                if (newDelivery == EDelivered.PENDING || newDelivery == EDelivered.PREPARE) {
                    throw new OrderException("You are in the delivery status");
                }
                break;
            case SUCCESS:
                if (newDelivery == EDelivered.PENDING || newDelivery == EDelivered.PREPARE || newDelivery == EDelivered.DELIVERY) {
                    throw new OrderException("You are in the successful delivery status");
                }
                break;
            case CANCEL:
                if (newDelivery != EDelivered.CANCEL) {
                    throw new OrderException("You are in the order cancellation status");
                }
                break;
            default:
                throw new OrderException("Invalid delivery status");
        }

        orders.setEDelivered(newDelivery);
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

    public Shipment findShipmentById(Long shipmentID) throws ShipmentException {
        Optional<Shipment> p = shipmentRepository.findById(shipmentID);
        return p.orElseThrow(() -> new ShipmentException("Shipment not found"));
    }

    public Payment findPaymentById(Long paymentId) throws PaymentException {
        Optional<Payment> p = paymentRepository.findById(paymentId);
        return p.orElseThrow(() -> new PaymentException("Shipment not found"));
    }

    public OrdersResponse order(Long userId, Long shipmentId, Long paymentId) throws UserException, ProductException, OrderException, ColorException, CategoryException, OrderDetailException, DiscountException, ShipmentException, CartItemException, PaymentException {
        Users u = findUserById(userId);
        Shipment shipment = findShipmentById(shipmentId);
        Payment payment = findPaymentById(paymentId);
        List<CartItemResponse> cartItemList = cartService.findAll();
        if (cartItemList.isEmpty()) {
            throw new CartItemException("Cart isEmpty!!!");
        }
        // Tạo một đơn hàng
        double totalPrice = cartItemList.stream()
                .mapToDouble(CartItemResponse::getPrice) // Chuyển đổi từ CartItemResponse thành giá tiền (double)
                .sum();

        Orders order = Orders.builder()
                .order_at(new Date())
                .eDelivered(EDelivered.PENDING)
                .users(u)
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
        u.getOrders().add(order);
        userRepository.save(u);
        return orderMapper.toResponse(order);
    }


}
