package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.security.model.domain.Product;
import ra.security.model.dto.request.ProductRequest;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IProductRepository;
import ra.security.security.jwt.JwtProvider;
import ra.security.service.IUserService;
import ra.security.service.impl.ProductService;
import ra.security.service.mapper.ProductMapper;
import ra.security.service.upload_aws.StorageService;

import java.util.List;

@RestController
@RequestMapping("/api/v4/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private StorageService service;

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<ProductResponse> addProduct(
            @ModelAttribute ProductRequest productRequest) {
        return new ResponseEntity<>(productService.save(productRequest), HttpStatus.CREATED);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.delete(id), HttpStatus.OK);
    }
}
