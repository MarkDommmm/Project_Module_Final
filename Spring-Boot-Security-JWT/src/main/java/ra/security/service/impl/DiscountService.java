package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.*;
import ra.security.model.domain.CartItem;
import ra.security.model.domain.Discount;
import ra.security.model.domain.Product;
import ra.security.model.domain.Shipment;
import ra.security.model.dto.request.DiscountRequest;
import ra.security.model.dto.response.CartItemResponse;
import ra.security.model.dto.response.DiscountResponse;
import ra.security.model.dto.response.ShipmentResponse;
import ra.security.repository.IDiscountRepsository;
import ra.security.repository.IProductRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.DiscountMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountService implements IGenericService<DiscountResponse, DiscountRequest, Long> {
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private IDiscountRepsository discountRepsository;
    @Autowired
    private CartService cartService;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<DiscountResponse> findAll() {
        return discountRepsository.findAll().stream()
                .map(d -> discountMapper.toResponse(d)).collect(Collectors.toList());
    }

    @Override
    public DiscountResponse findById(Long aLong) throws CustomException {
        Optional<Discount> discount = discountRepsository.findById(aLong);
        return discount.map(item -> discountMapper.toResponse(item)).orElseThrow(() ->
                new CustomException(("Discount not found")));
    }

    @Override
    public DiscountResponse save(DiscountRequest discountRequest) throws CustomException {
        if (discountRepsository.existsByName(discountRequest.getName())) {
            throw new CustomException("Discount already exists");
        }
        return discountMapper.toResponse(discountRepsository.save(discountMapper.toEntity(discountRequest)));
    }

    @Override
    public DiscountResponse update(DiscountRequest discountRequest, Long id) {
        Discount discount = discountMapper.toEntity(discountRequest);
        discount.setId(id);
        return discountMapper.toResponse(discountRepsository.save(discount));
    }

    @Override
    public DiscountResponse delete(Long aLong) throws CustomException {
        Optional<Discount> discount = discountRepsository.findById(aLong);
        if (discount.isPresent()) {
            discountRepsository.deleteById(aLong);
            return discountMapper.toResponse(discount.get());
        }
        throw new CustomException("Discount not found");
    }

    public List<DiscountResponse> findShipmentsById(Long discountID) throws CustomException {
        List<Discount> discount = discountRepsository.findAll();
        List<DiscountResponse> discountResponses = new ArrayList<>();
        boolean userHasShipments = false;

        for (Discount s : discount) {
            if (s.getId().equals(discountID)) {
                discountResponses.add(discountMapper.toResponse(s));
                userHasShipments = true;
            }
        }

        if (!userHasShipments) {
            throw new CustomException("User doesn't have any shipments");
        }

        return discountResponses;
    }



}
