package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.advice.LoginException;
import ra.security.exception.*;
import ra.security.model.dto.request.BrandRequest;
import ra.security.model.dto.request.ShipmentRequest;
import ra.security.model.dto.response.BrandResponse;
import ra.security.model.dto.response.ShipmentResponse;
import ra.security.service.impl.ShipmentService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v4/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllShipments(HttpSession session) throws ShipmentException {
        return new ResponseEntity<>(shipmentService.findShipmentsByUser(session.getAttribute("CurrentUser")), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ShipmentResponse> addShipment(@Valid
            @RequestBody ShipmentRequest shipmentRequest,
            HttpSession session)
            throws ShipmentException, LoginException, ColorException, CategoryException, BrandException, DiscountException {
        return new ResponseEntity<>(shipmentService.add(shipmentRequest,session.getAttribute("CurrentUser")), HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ShipmentResponse> getShipment(@PathVariable Long id) throws ColorException, CategoryException, OrderDetailException, DiscountException, ShipmentException, OrderException {
        return new ResponseEntity<>(shipmentService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShipmentResponse> updateShipment(@PathVariable Long id, @RequestBody ShipmentRequest shipmentRequest) {
        return new ResponseEntity<>(shipmentService.update(shipmentRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ShipmentResponse> deleteShipment(@PathVariable Long id) {
        return new ResponseEntity<>(shipmentService.delete(id), HttpStatus.OK);
    }
}
