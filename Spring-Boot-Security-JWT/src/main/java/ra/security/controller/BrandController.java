package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.BrandException;
import ra.security.exception.ColorException;
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
    public ResponseEntity<BrandResponse> addBrand(@RequestBody BrandRequest brandRequest) throws  BrandException {
        return new ResponseEntity<>(brandService.save(brandRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BrandResponse> getBrand(@PathVariable Long id)     {
        return new ResponseEntity<>(brandService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable Long id, @RequestBody BrandRequest brandRequest) {
        return new ResponseEntity<>(brandService.update(brandRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BrandResponse> deleteBrand(@PathVariable Long id) {
        return new ResponseEntity<>(brandService.delete(id), HttpStatus.OK);
    }
}
