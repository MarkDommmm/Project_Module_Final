package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.ColorException;
import ra.security.model.dto.request.ColorRequest;
import ra.security.model.dto.request.ProductRequest;
import ra.security.model.dto.response.ColorResponse;
import ra.security.model.dto.response.ProductResponse;
import ra.security.service.impl.ColorService;
import ra.security.service.impl.ProductService;

@RestController
@RequestMapping("/api/v4/admin")
@CrossOrigin("*")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ColorService colorService;
    @PostMapping("/addProduct")
    private ResponseEntity<ProductResponse> addProduct(
            @ModelAttribute ProductRequest productRequest) {
        return new ResponseEntity<>(productService.save(productRequest), HttpStatus.CREATED);
    }
    @PostMapping("/addColor")
    public ResponseEntity<ColorResponse> addColor(@RequestBody ColorRequest colorRequest) throws ColorException {
        return new ResponseEntity<>(colorService.save(colorRequest), HttpStatus.CREATED);
    }
}
