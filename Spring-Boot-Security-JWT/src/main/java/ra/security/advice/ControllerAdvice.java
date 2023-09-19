package ra.security.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.security.exception.*;

import javax.persistence.EntityExistsException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginFail(LoginException loginException) {
        return new ResponseEntity<>(loginException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> loginFails(AccessDeniedException loginException) {
        return new ResponseEntity<>("Ban khong du tu cach truy cap", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> CheckUsername(EntityExistsException entityExistsException) {
        return new ResponseEntity<>(entityExistsException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validateUsername(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> err = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(
                fieldError -> {
                    err.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
        );
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserException.class)
    public String handleExceptionUser(UserException e) {
        return "Exception user --> " + e.getMessage();
    }

    //
//    @ExceptionHandler(RoleException.class)
//    public String handleExceptionRole(RoleException e) {
//        return "Exception role --> " + e.getMessage();
//    }
    @ExceptionHandler(OrderDetailException.class)
    public String handleExceptionOrderDetail(OrderDetailException e) {
        return "Exception user --> " + e.getMessage();
    }

    @ExceptionHandler(CategoryException.class)
    public String handleExceptionCategory(CategoryException e) {
        return "Exception category --> " + e.getMessage();
    }

    @ExceptionHandler(BrandException.class)
    public String handleExceptionBrand(BrandException e) {
        return "Exception brand --> " + e.getMessage();
    }

    @ExceptionHandler(DiscountException.class)
    public String handleExceptionCoupon(DiscountException e) {
        return "Exception coupon --> " + e.getMessage();
    }

    @ExceptionHandler(OrderException.class)
    public String handleExceptionOrder(OrderException e) {
        return "Exception order --> " + e.getMessage();
    }

    @ExceptionHandler(CartItemException.class)
    public String handleExceptionCartItem(CartItemException e) {
        return "Exception cartItem --> " + e.getMessage();
    }

    @ExceptionHandler(ProductException.class)
    public String handleExceptionProduct(ProductException e) {
        return "Exception product --> " + e.getMessage();
    }

    @ExceptionHandler(ImageProductException.class)
    public String handleExceptionImage(ImageProductException e) {
        return "Exception image --> " + e.getMessage();
    }

    @ExceptionHandler(ShipmentException.class)
    public String handleExceptionShipment(ShipmentException e) {
        return "Exception shipment --> " + e.getMessage();
    }
    @ExceptionHandler(PaymentException.class)
    public String handleExceptionPayment(PaymentException e) {
        return "Exception payment --> " + e.getMessage();
    }

}
