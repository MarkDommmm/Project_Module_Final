package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.CustomException;
import ra.security.model.dto.request.BrandRequest;
import ra.security.model.dto.response.BrandResponse;
import ra.security.service.impl.BrandService;

import java.util.List;

@RestController
@RequestMapping("/api/v4/admin/brand")
@CrossOrigin("*")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/getAll")
    public ResponseEntity<List<BrandResponse>> getBrands() {
        return new ResponseEntity<>(brandService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<BrandResponse> addBrand(@RequestBody BrandRequest brandRequest) throws CustomException {
        return new ResponseEntity<>(brandService.save(brandRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBrand(@PathVariable Long id) throws CustomException {
        try {
            return new ResponseEntity<>(brandService.findById(id), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Brand not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable Long id, @RequestBody BrandRequest brandRequest) throws CustomException {
        return new ResponseEntity<>(brandService.update(brandRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) throws  CustomException {
        return new ResponseEntity<>(brandService.delete(id), HttpStatus.OK);

    }
}
