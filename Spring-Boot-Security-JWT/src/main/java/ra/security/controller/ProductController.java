package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ra.security.exception.CustomException;
import ra.security.model.dto.request.ProductRequest;
import ra.security.model.dto.request.ProductUpdateRequest;
import ra.security.model.dto.response.ProductResponse;
import ra.security.service.impl.ImageProductService;
import ra.security.service.impl.ProductService;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v4")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ImageProductService imageProductService;


    @GetMapping("/public/products/getAll")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return new ResponseEntity<>(productService.findAllInUser(), HttpStatus.OK);
    }
    @GetMapping("/admin/products/getAll")
    public ResponseEntity<List<ProductResponse>> getProductsAdmin() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/admin/products/add")
    public ResponseEntity<?> addProduct(@Valid @ModelAttribute ProductRequest productRequest, BindingResult bindingResult) throws  CustomException {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            productService.save(productRequest);
            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<?> violation : violations) {
                errorMessages.add(violation.getMessage());
            }
            return ResponseEntity.badRequest().body(errorMessages);
        }
    }


    @PutMapping("/admin/products/update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,@Valid
            @RequestBody ProductUpdateRequest productRequest) {
        return new ResponseEntity<>(productService.updateProduct(productRequest, productId), HttpStatus.OK);
    }

    @GetMapping("/admin/products/get/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws CustomException {
        productService.delete(id);
        return new ResponseEntity<>("Delete success", HttpStatus.OK);
    }

    @GetMapping("/admin/changeStatus/{id}")
    public ResponseEntity<ProductResponse> handleChangeStatusProduct(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(productService.changeStatus(id), HttpStatus.OK);
    }

    @GetMapping("/admin/product/{productId}/changeBrand/{brandId}")
    public ResponseEntity<ProductResponse> handleChangeBrandProduct(
            @PathVariable Long brandId,
            @PathVariable Long productId) throws CustomException {
        return new ResponseEntity<>(productService.changeBrand(productId,brandId), HttpStatus.OK);
    }

    @PutMapping("/admin/addImage/toProduct/{id}")
    public ResponseEntity<?> handleAddImageToProduct(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id) throws CustomException {
       if (multipartFile.isEmpty()) {
           return new ResponseEntity<>("File not null!!!", HttpStatus.BAD_REQUEST);

       }
        return new ResponseEntity<>(productService.addImageToProduct(multipartFile, id), HttpStatus.CREATED);
    }

    @PutMapping("/admin/changeImage/toProduct/{id}")
    public ResponseEntity<ProductResponse> handleChangeImage(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(productService.changeImageProduct(multipartFile, id), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/admin/deleteImage/{idImage}/inProduct/{idProduct}")
    public ResponseEntity<ProductResponse> handleDeleteImageInProduct(@PathVariable Long idImage, @PathVariable Long idProduct) throws CustomException {
        return new ResponseEntity<>(productService.deleteImageInProduct(idImage, idProduct), HttpStatus.OK);
    }
}
