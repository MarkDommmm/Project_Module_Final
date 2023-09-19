package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.advice.LoginException;
import ra.security.exception.*;
import ra.security.model.domain.Shipment;
import ra.security.model.domain.Users;
import ra.security.model.dto.request.ShipmentRequest;
import ra.security.model.dto.response.ShipmentResponse;
import ra.security.repository.IShipmentRepository;
import ra.security.repository.IUserRepository;
import ra.security.service.IGenericService;
import ra.security.service.IUserService;
import ra.security.service.mapper.ShipmentMapper;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipmentService implements IGenericService<ShipmentResponse, ShipmentRequest, Long> {
    @Autowired
    private IShipmentRepository shipmentRepository;
    @Autowired
    private ShipmentMapper shipmentMapper;
    @Autowired
    private IUserService userService;

    @Override
    public List<ShipmentResponse> findAll() {
        return shipmentRepository.findAll().stream()
                .map(c -> shipmentMapper.toResponse(c)).collect(Collectors.toList());
    }

    public List<ShipmentResponse> findShipmentsByUser(Object username) throws ShipmentException {
        List<Shipment> shipments = shipmentRepository.findAll();
        List<ShipmentResponse> userShipments = new ArrayList<>();
        boolean userHasShipments = false;

        for (Shipment s : shipments) {
            if (s.getUser().getUsername().equals(username)) {
                userShipments.add(shipmentMapper.toResponse(s));
                userHasShipments = true; // Đánh dấu rằng người dùng có ít nhất một lô hàng
            }
        }

        if (!userHasShipments) {
            throw new ShipmentException("User doesn't have any shipments");
        }

        return userShipments;
    }



    @Override
    public ShipmentResponse findById(Long aLong) throws CategoryException, ColorException, OrderException, DiscountException, OrderDetailException, ShipmentException {
        Optional<Shipment> shipment = shipmentRepository.findById(aLong);
        return shipment.map(c -> shipmentMapper.toResponse(c)).orElseThrow(() ->
                new ShipmentException("Shipment not found"));
    }

    @Override
    public ShipmentResponse save(ShipmentRequest shipmentRequest) throws CategoryException, BrandException, ColorException, DiscountException, ShipmentException {
        if (shipmentRepository.existsByAddress(shipmentRequest.getAddress())) {
            throw new ShipmentException("Shipment already exists");
        }
        return shipmentMapper.toResponse(shipmentRepository.save(shipmentMapper.toEntity(shipmentRequest)));
    }

    public ShipmentResponse add(ShipmentRequest shipmentRequest, Object users) throws CategoryException, BrandException, ColorException, DiscountException, ShipmentException, LoginException {
        if (shipmentRepository.existsByAddress(shipmentRequest.getAddress())) {
            throw new ShipmentException("Shipment already exists");
        }
        if (users == null) {
            throw new LoginException("Please login!!!");
        }
        Optional<Users> u = userService.findByUserName(String.valueOf(users));
        ShipmentRequest request = ShipmentRequest.builder()
                .user(u.get())
                .email(shipmentRequest.getEmail())
                .phone(shipmentRequest.getPhone())
                .address(shipmentRequest.getAddress())
                .create_at(shipmentRequest.getCreate_at())
                .orders(shipmentRequest.getOrders())
                .build();

        return shipmentMapper.toResponse(shipmentRepository.save(shipmentMapper.toEntity(request)));
    }


    @Override
    public ShipmentResponse update(ShipmentRequest shipmentRequest, Long id) {
        Shipment shipment = shipmentMapper.toEntity(shipmentRequest);
        shipment.setId(id);
        return shipmentMapper.toResponse(shipmentRepository.save(shipment));
    }

    @Override
    public ShipmentResponse delete(Long aLong) {
        Optional<Shipment> shipment = shipmentRepository.findById(aLong);
        if (shipment.isPresent()) {
            shipmentRepository.deleteById(aLong);
            return shipmentMapper.toResponse(shipment.get());
        }
        return null;
    }
}
