package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.*;
import ra.security.model.domain.Orders;
import ra.security.model.domain.Payment;
import ra.security.model.domain.Product;
import ra.security.model.dto.request.PaymentRequest;
import ra.security.model.dto.response.PaymentResponse;
import ra.security.repository.IPaymentRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.PaymentMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService implements IGenericService<PaymentResponse, PaymentRequest, Long> {
    @Autowired
    private IPaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll().stream()
                .map((o ->paymentMapper.toResponse(o))).collect(Collectors.toList());
    }

    @Override
    public PaymentResponse findById(Long aLong) throws  PaymentException {
        Optional<Payment> payment = paymentRepository.findById(aLong);
        return payment.map(o -> paymentMapper.toResponse(o)).orElseThrow(() ->
                new PaymentException("Order not found"));
    }


    @Override
    public PaymentResponse save(PaymentRequest paymentRequest) throws CategoryException, BrandException, ColorException, DiscountException, ShipmentException {
        return paymentMapper.toResponse(paymentRepository.save(paymentMapper.toEntity(paymentRequest)));
    }

    @Override
    public PaymentResponse update(PaymentRequest paymentRequest, Long id) {
        Payment o = paymentMapper.toEntity(paymentRequest);
        o.setId(id);
        return paymentMapper.toResponse(paymentRepository.save(o));
    }

    @Override
    public PaymentResponse delete(Long aLong) {
        Optional<Payment> p = paymentRepository.findById(aLong);
        if (p.isPresent()) {
            paymentRepository.deleteById(aLong);
            return paymentMapper.toResponse(p.get());
        }
        return null;
    }
}
