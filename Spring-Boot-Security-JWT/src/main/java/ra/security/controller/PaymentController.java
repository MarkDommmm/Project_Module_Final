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
@RequestMapping("/api/v4/auth/payments")
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentResponse>> getBrands() {
        return new ResponseEntity<>(paymentService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<PaymentResponse> addBrand(@RequestBody PaymentRequest paymentRequest) throws ShipmentException, ColorException, CategoryException, BrandException, DiscountException {
        return new ResponseEntity<>(paymentService.save(paymentRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PaymentResponse> getBrand(@PathVariable Long id) throws PaymentException {
        return new ResponseEntity<>(paymentService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PaymentResponse> updateBrand(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.update(paymentRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PaymentResponse> deleteBrand(@PathVariable Long id) {
        return new ResponseEntity<>(paymentService.delete(id), HttpStatus.OK);
    }
}
