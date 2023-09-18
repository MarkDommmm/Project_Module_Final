package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.OrderException;
import ra.security.exception.ProductException;
import ra.security.exception.UserException;
import ra.security.model.dto.response.OrdersResponse;
import ra.security.service.impl.CartService;
import ra.security.service.impl.OrderService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v4/order")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @PostMapping("/buy/{userId}")
    public ResponseEntity<OrdersResponse> order(@PathVariable Long userId)
            throws UserException, ProductException, OrderException {
        return new ResponseEntity<>(orderService.order(userId), HttpStatus.OK);
    }

}
