package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.security.exception.ImageProductException;
import ra.security.exception.ProductException;
import ra.security.model.domain.Product;
import ra.security.model.dto.request.ProductRequest;
import ra.security.model.dto.request.ProductUpdateRequest;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IProductRepository;
import ra.security.security.jwt.JwtProvider;
import ra.security.service.IUserService;
import ra.security.service.impl.ImageProductService;
import ra.security.service.impl.ProductService;
import ra.security.service.mapper.ProductMapper;
import ra.security.service.upload_aws.StorageService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/v4")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ImageProductService imageProductService;

    @GetMapping("/auth/products/getAll")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }


    @PostMapping("/admin/products/add")
    public ResponseEntity<ProductResponse> addProduct(
            @ModelAttribute ProductRequest productRequest) {
        return new ResponseEntity<>(productService.save(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/admin/products/update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequest productRequest) {
        return new ResponseEntity<>(productService.updateProduct(productRequest, productId), HttpStatus.OK);
    }

    @GetMapping("/admin/products/get/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/delete/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/admin/changeStatus/{id}")
    public ResponseEntity<ProductResponse> handleChangeStatusProduct(@PathVariable Long id) throws ProductException {
        return new ResponseEntity<>(productService.changeStatus(id), HttpStatus.OK);
    }

    @GetMapping("/admin/product/{productId}/changeBrand/{brandId}")
    public ResponseEntity<ProductResponse> handleChangeBrandProduct(
            @PathVariable Long brandId,
            @PathVariable Long productId) throws ProductException {
        return new ResponseEntity<>(productService.changeBrand(productId,brandId), HttpStatus.OK);
    }

    @PutMapping("/addImage/toProduct/{id}")
    public ResponseEntity<ProductResponse> handleAddImageToProduct(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id) throws ProductException {
        return new ResponseEntity<>(productService.addImageToProduct(multipartFile, id), HttpStatus.CREATED);
    }

    @PutMapping("/changeImage/toProduct/{id}")
    public ResponseEntity<ProductResponse> handleChangeImage(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id) throws ProductException {
        return new ResponseEntity<>(productService.changeImageProduct(multipartFile, id), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/deleteImage/{idImage}/inProduct/{idProduct}")
    public ResponseEntity<ProductResponse> handleDeleteImageInProduct(@PathVariable Long idImage, @PathVariable Long idProduct) throws ImageProductException, ProductException, ImageProductException {
        return new ResponseEntity<>(productService.deleteImageInProduct(idImage, idProduct), HttpStatus.OK);
    }
}
