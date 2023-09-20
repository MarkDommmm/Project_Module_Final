package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.*;
import ra.security.model.domain.EDelivered;
import ra.security.model.dto.request.OrdersRequest;
import ra.security.model.dto.response.OrdersResponse;
import ra.security.service.IUserService;
import ra.security.service.impl.CartService;
import ra.security.service.impl.OrderService;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/api/v4")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/auth/order/buy/shipment/{shipmentId}/payment/{paymentId}/discount/{discountName}")
    public ResponseEntity<OrdersResponse> order(HttpSession session,
                                                @PathVariable Long paymentId,
                                                @PathVariable Long shipmentId)
            throws Exception {
        return new ResponseEntity<>(orderService.order(session.getAttribute("CurrentUser"), shipmentId, paymentId), HttpStatus.OK);
    }


    @GetMapping("/auth/order/getAll")
    public ResponseEntity<List<OrdersResponse>> getOrder(HttpSession session) {
        return new ResponseEntity<>(orderService.showOrders(session.getAttribute("CurrentUser")), HttpStatus.OK);
    }
    @PutMapping("/admin/order/change_delivery/{orderId}")
    public ResponseEntity<OrdersResponse> changeDeliveryOrder(@PathVariable Long orderId) throws CustomException {
        return new ResponseEntity<>(orderService.changeDelivery(orderId), HttpStatus.OK);
    }
    @PutMapping("/admin/order/confirmOrder/{orderId}")
    public ResponseEntity<OrdersResponse> ConfirmOrder(@PathVariable Long orderId,@RequestParam("type")String type) throws CustomException {
        return new ResponseEntity<>(orderService.confirmOrder(orderId,type), HttpStatus.OK);
    }

}
