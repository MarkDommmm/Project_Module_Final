package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.CustomException;
import ra.security.model.domain.CartItem;
import ra.security.model.domain.Discount;
import ra.security.model.dto.request.DiscountRequest;
import ra.security.model.dto.response.CartItemResponse;
import ra.security.model.dto.response.DiscountResponse;
import ra.security.repository.IDiscountRepsository;
import ra.security.service.impl.CartService;
import ra.security.service.impl.DiscountService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/v4")
@CrossOrigin("*")
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private CartService cartService;

    @GetMapping("/admin/discounts/getAll")
    public ResponseEntity<List<DiscountResponse>> getDiscounts() {
        return new ResponseEntity<>(discountService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/admin/discounts/add")
    public ResponseEntity<DiscountResponse> addDiscount(@Valid @RequestBody DiscountRequest discount) throws CustomException {
        return new ResponseEntity<>(discountService.save(discount), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/discounts/delete/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(discountService.delete(id), HttpStatus.OK);
    }

    @PutMapping("/admin/discounts/update/{id}")
    public ResponseEntity<DiscountResponse> updateDiscount(@PathVariable Long id,
                                                           @Valid @RequestBody DiscountRequest discountRequest) {
        return new ResponseEntity<>(discountService.update(discountRequest, id), HttpStatus.OK);
    }

    @GetMapping("/admin/discounts/get/{id}")
    public ResponseEntity<DiscountResponse> getDiscount(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(discountService.findById(id), HttpStatus.OK);
    }


}
