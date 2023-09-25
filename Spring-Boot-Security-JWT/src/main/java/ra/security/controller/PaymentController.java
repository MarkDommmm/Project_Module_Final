package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.*;
import ra.security.model.dto.request.BrandRequest;
import ra.security.model.dto.request.PaymentRequest;
import ra.security.model.dto.response.BrandResponse;
import ra.security.model.dto.response.PaymentResponse;
import ra.security.service.impl.BrandService;
import ra.security.service.impl.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/v4")
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/auth/payments/getAll")
    public ResponseEntity<List<PaymentResponse>> getPayments() {
        return new ResponseEntity<>(paymentService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/admin/payments/add")
    public ResponseEntity<PaymentResponse> addPayment(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.save(paymentRequest), HttpStatus.CREATED);
    }

    @GetMapping("/admin/payments/get/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(paymentService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/admin/payments/update/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.update(paymentRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/admin/payments/delete/{id}")
    public ResponseEntity<PaymentResponse> deletePayment(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(paymentService.delete(id), HttpStatus.OK);
    }
}
