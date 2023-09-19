package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.LoginException;
import ra.security.exception.*;
import ra.security.model.domain.Shipment;
import ra.security.model.domain.Users;
import ra.security.model.dto.request.ShipmentRequest;
import ra.security.model.dto.response.ShipmentResponse;
import ra.security.repository.IShipmentRepository;
import ra.security.service.IGenericService;
import ra.security.service.IUserService;
import ra.security.service.mapper.ShipmentMapper;

import java.util.ArrayList;
import java.util.List;
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

    public List<ShipmentResponse> findShipmentsByUser(Object username) throws CustomException {
        List<Shipment> shipments = shipmentRepository.findAll();
        List<ShipmentResponse> userShipments = new ArrayList<>();
        boolean userHasShipments = false;

        for (Shipment s : shipments) {
            if (s.getUser().getUsername().equals(username)) {
                userShipments.add(shipmentMapper.toResponse(s));
                userHasShipments = true;
            }
        }

        if (!userHasShipments) {
            throw new CustomException("User doesn't have any shipments");
        }

        return userShipments;
    }


    @Override
    public ShipmentResponse findById(Long aLong) throws  CustomException {
        Optional<Shipment> shipment = shipmentRepository.findById(aLong);
        return shipment.map(c -> shipmentMapper.toResponse(c)).orElseThrow(() ->
                new CustomException("Shipment not found"));
    }

    @Override
    public ShipmentResponse save(ShipmentRequest shipmentRequest) throws CustomException {
        if (shipmentRepository.existsByAddress(shipmentRequest.getAddress())) {
            throw new CustomException("Shipment already exists");
        }
        return shipmentMapper.toResponse(shipmentRepository.save(shipmentMapper.toEntity(shipmentRequest)));
    }

    public ShipmentResponse add(ShipmentRequest shipmentRequest, Object users) throws CustomException, LoginException {
        if (shipmentRepository.existsByAddress(shipmentRequest.getAddress())) {
            throw new CustomException("Shipment already exists");
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

    public ShipmentResponse update(ShipmentRequest shipmentRequest, Long id, Object user) {
        Optional<Users> users = userService.findByUserName(String.valueOf(user));
        Shipment shipment = shipmentMapper.toEntity(shipmentRequest);
        shipment.setId(id);
        shipment.setUser(users.get());
        return shipmentMapper.toResponse(shipmentRepository.save(shipment));
    }

    @Override
    public ShipmentResponse delete(Long aLong) throws CustomException {
        Optional<Shipment> shipment = shipmentRepository.findById(aLong);
        if (shipment.isPresent()) {
            shipmentRepository.deleteById(aLong);
            return shipmentMapper.toResponse(shipment.get());
        }
        throw new CustomException("Shipment not found");

    }
}
