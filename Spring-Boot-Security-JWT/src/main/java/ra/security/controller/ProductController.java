package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.security.model.domain.Products;
import ra.security.model.dto.request.ProductRequest;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IProductRepository;
import ra.security.security.jwt.JwtProvider;
import ra.security.service.IUserService;
import ra.security.service.impl.ProductService;
import ra.security.service.mapper.ProductMapper;

import java.util.List;

@RestController
@RequestMapping("/api/v4/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<ProductResponse> addProduct(
            @RequestParam(value = "file") MultipartFile file,
            @RequestBody ProductRequest productRequest) {
//
//        // Xử lý tệp (file) và lưu trữ nó, ví dụ, sử dụng một phương thức tách riêng
//        String imageUrl = productMapper.uploadFile(file);
//
//        // Gán URL của tệp vào trường image của productRequest
//        productRequest.setImage(imageUrl);
//
//        // Gọi productService để lưu sản phẩm
        ProductResponse response = productService.save(productRequest);



        // Trả về kết quả
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
