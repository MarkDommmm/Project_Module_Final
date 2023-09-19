package ra.security.service.mapper;

import org.springframework.stereotype.Component;
import ra.security.model.domain.Payment;
import ra.security.model.dto.request.PaymentRequest;
import ra.security.model.dto.response.PaymentResponse;
import ra.security.service.IGenericMapper;

import java.util.Date;

@Component
public class PaymentMapper implements IGenericMapper<Payment, PaymentRequest, PaymentResponse> {
    @Override
    public Payment toEntity(PaymentRequest paymentRequest) {
        return Payment.builder()

                .created_at(new Date())

                .provider(paymentRequest.getProvider())
                .orders(paymentRequest.getOrders())
                .status(paymentRequest.isStatus())
                .build();
    }

    @Override
    public PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())

                .created_at(new Date())

                .provider(payment.getProvider())
                .orders(payment.getOrders())
                .status(payment.isStatus())
                .build();
    }
}
