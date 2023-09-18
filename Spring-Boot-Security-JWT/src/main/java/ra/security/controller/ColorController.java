package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.ColorException;
import ra.security.model.dto.request.ColorRequest;
import ra.security.model.dto.response.ColorResponse;
import ra.security.service.impl.ColorService;

import java.util.List;

@RestController
@RequestMapping("/api/v4/auth/colors")
@CrossOrigin("*")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ColorResponse>> getColors() {
        return new ResponseEntity<>(colorService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ColorResponse> addColor(@RequestBody ColorRequest colorRequest) throws ColorException {
        return new ResponseEntity<>(colorService.save(colorRequest), HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ColorResponse> getColor(@PathVariable Long id) throws ColorException {
        return new ResponseEntity<>(colorService.findById(id),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ColorResponse> updateColor(@PathVariable Long id,@RequestBody ColorRequest colorRequest){
        return  new ResponseEntity<>(colorService.update(colorRequest,id),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ColorResponse> deleteColor(@PathVariable Long id) {
        return new ResponseEntity<>(colorService.delete(id), HttpStatus.OK);
    }
}
