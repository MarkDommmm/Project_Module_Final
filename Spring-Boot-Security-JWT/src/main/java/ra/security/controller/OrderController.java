package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.*;
import ra.security.model.domain.EDelivered;
import ra.security.model.dto.request.OrdersRequest;
import ra.security.model.dto.response.OrdersResponse;
import ra.security.service.impl.CartService;
import ra.security.service.impl.OrderService;



@RestController
@RequestMapping("/api/v4/auth/order")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @PostMapping("/buy/{userId}/shipment/{shipmentId}/payment/{paymentId}")
    public ResponseEntity<OrdersResponse> order(@PathVariable Long userId,
                                                @PathVariable Long paymentId,
                                                @PathVariable Long shipmentId)
            throws UserException, ProductException, OrderException, ColorException, CategoryException, OrderDetailException, DiscountException, ShipmentException, CartItemException, PaymentException {
        return new ResponseEntity<>(orderService.order(userId,shipmentId,paymentId), HttpStatus.OK);
    }

    @PutMapping("/changeDelivery/{orderId}")
    public ResponseEntity<OrdersResponse> changeDelivery(@PathVariable Long orderId,
                                                         @RequestBody EDelivered status) throws OrderException {

        return new ResponseEntity<>(orderService.changeDelivery(status, orderId), HttpStatus.OK);
    }


}
