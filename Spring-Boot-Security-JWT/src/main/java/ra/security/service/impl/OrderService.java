package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.*;
import ra.security.model.domain.*;
import ra.security.model.dto.request.OrdersRequest;
import ra.security.model.dto.response.*;
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
    private CartService cartService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private IShipmentRepository shipmentRepository;
    @Autowired
    private IPaymentRepository paymentRepository;
    @Autowired
    private IDiscountRepsository discountRepsository;
    @Autowired
    private OrderDetailService orderDetailService;

    public List<OrderDetailsDTO> getAllOrders() {
        List<OrdersResponse> ordersResponse = findAll();
        List<OrderDetailsResponse> orderDetailsResponses = orderDetailService.findAll();
        List<OrderDetailsDTO> detailsDTOList = new ArrayList<>();

        for (OrdersResponse o : ordersResponse) {
            List<OrderDetailsResponse> orderDetailsDTOS = new ArrayList<>(); // Khởi tạo danh sách mới cho mỗi OrdersResponse

            for (OrderDetailsResponse od : orderDetailsResponses) {
                if (o.getId().equals(od.getOrders().getId())) {
                    orderDetailsDTOS.add(od);
                }
            }

            OrderDetailsDTO dto = OrderDetailsDTO.builder()
                    .orders(o)
                    .orderDetailsList(orderDetailsDTOS)
                    .build();
            detailsDTOList.add(dto);
        }

        return detailsDTOList;
    }

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

    public OrdersResponse changeDelivery(Long id) throws CustomException {
        Orders orders = findOrderById(id);
        EDelivered type = orders.getEDelivered();
        if (!orders.isStatus()) {
            throw new CustomException("Order cannot be edited anymore!! ");
        }
        if (type == EDelivered.PENDING) {
            orders.setEDelivered(EDelivered.PREPARE);
        }
        if (type == EDelivered.PREPARE) {
            orders.setEDelivered(EDelivered.DELIVERY);
        }
        return orderMapper.toResponse(orderRepository.save(orders));
    }

    public OrdersResponse confirmOrder(Long id, String type) throws CustomException {
        Orders orders = findOrderById(id);

        EDelivered eDelivered = findDeliveryByInput(type);
        if (!orders.isStatus()) {
            throw new CustomException("Order cannot be edited anymore!! ");
        }
        if (eDelivered == EDelivered.SUCCESS) {
            orders.setEDelivered(eDelivered);
            orders.setStatus(false);
        }
        if (eDelivered == EDelivered.CANCEL) {
            List<OrderDetails> orderDetails = orderDetailRepository.findAll();
            for (OrderDetails p : orderDetails) {
                Optional<Product> productResponse = productRepository.findById(p.getProducts().getId());
                if (p.getOrders().getId().equals(orders.getId())) {
                    if (p.getProducts().getId().equals(productResponse.get().getId())) {
                        productResponse.get().setStock(productResponse.get().getStock() + p.getQuantity());
                    }
                }

            }
            orders.setEDelivered(eDelivered);
            orders.setStatus(false);
        }
        return orderMapper.toResponse(orderRepository.save(orders));
    }

    public EDelivered findDeliveryByInput(String typeDelivery) throws CustomException {
        switch (typeDelivery) {
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

    public OrdersResponse order(Object user, Long shipmentId, Long paymentId, String discountName) throws CustomException {
        Optional<Users> u = userRepository.findByUsername(String.valueOf(user));
        Shipment shipment = findShipmentById(shipmentId);
        List<ShipmentResponse> check = shipmentService.findShipmentsByUser(user);
        Discount discount = discountRepsository.findByName(discountName);
        if (check == null) {
            throw new CustomException("Shipment not found");
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
        Orders orders = null;
        if (discount != null) {
            orders = Orders.builder()
                    .order_at(new Date())
                    .eDelivered(EDelivered.PENDING)
                    .users(u.get())
                    .shipment(shipment)
                    .payment(payment)
                    .discount(discount)
                    .total_price(totalPrice - discount.getPromotion_price())
                    .status(true)
                    .build();
            orderRepository.save(orders);
            discount.setStock(discount.getStock() - 1);
            discountRepsository.save(discount);
        } else {
            orders = Orders.builder()
                    .order_at(new Date())
                    .eDelivered(EDelivered.PENDING)
                    .users(u.get())
                    .shipment(shipment)
                    .payment(payment)
                    .discount(null)
                    .total_price(totalPrice)
                    .status(true)
                    .build();
            orderRepository.save(orders);
        }

        for (CartItemResponse p : cartItemList) {
            Product product = findProductById(p.getIdProduct());
            product.setStock(product.getStock() - p.getQuantity());
            productRepository.save(product);
            OrderDetails orderDetails = OrderDetails.builder()
                    .orders(orders)
                    .created_at(new Date())
                    .quantity(p.getQuantity())
                    .products(product)
                    .price(p.getPrice())
                    .build();
            orderDetailRepository.save(orderDetails);
        }
        cartService.clearCartList();
        return orderMapper.toResponse(orders);
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

//

}
