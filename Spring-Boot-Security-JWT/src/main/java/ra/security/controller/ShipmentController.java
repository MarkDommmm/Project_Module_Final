package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.security.exception.LoginException;
import ra.security.exception.*;
import ra.security.model.domain.Shipment;
import ra.security.model.domain.Users;
import ra.security.model.dto.request.ShipmentRequest;
import ra.security.model.dto.response.ShipmentResponse;
import ra.security.service.IUserService;
import ra.security.service.impl.ShipmentService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v4/auth/shipments")
public class ShipmentController {
    @Autowired
    private IUserService userService;

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllShipments(HttpSession session) throws CustomException {
        return new ResponseEntity<>(shipmentService.findShipmentsByUser(session.getAttribute("CurrentUser")), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ShipmentResponse> addShipment(@Valid
            @RequestBody ShipmentRequest shipmentRequest,
            HttpSession session)
            throws CustomException, LoginException {
        return new ResponseEntity<>(shipmentService.add(shipmentRequest,session.getAttribute("CurrentUser")), HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getShipment(@PathVariable Long id)  {
       try {
           return new ResponseEntity<>(shipmentService.findById(id), HttpStatus.OK);
       }catch (CustomException e){
           return  new ResponseEntity<>("Shipment not found!!!",HttpStatus.BAD_REQUEST);
       }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShipmentResponse> updateShipment(HttpSession session,@PathVariable Long id, @RequestBody ShipmentRequest shipmentRequest) {
        return new ResponseEntity<>(shipmentService.update(shipmentRequest, id,session.getAttribute("CurrentUser")), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{shipmentId}")
    public ResponseEntity<String> deleteShipment(@PathVariable Long shipmentId, Principal principal) throws CustomException {
        Optional<Users> currentUser = userService.findByUserName(principal.getName());
        ShipmentResponse shipment = shipmentService.findById(shipmentId);
        if (currentUser.get().getId().equals(shipment.getUser_id())) {
            shipmentService.delete(shipmentId);
            return ResponseEntity.ok("Shipment deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this shipment.");
        }
    }
}
