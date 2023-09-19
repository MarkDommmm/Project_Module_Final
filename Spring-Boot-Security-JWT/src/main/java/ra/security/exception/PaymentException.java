package ra.security.exception;

import org.springframework.stereotype.Component;


public class PaymentException  extends Exception{
    public PaymentException(String message) {
        super(message);
    }
}
