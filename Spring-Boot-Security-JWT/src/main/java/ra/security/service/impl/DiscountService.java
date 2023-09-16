package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.*;
import ra.security.model.domain.Discount;
import ra.security.model.dto.request.DiscountRequest;
import ra.security.model.dto.response.DiscountResponse;
import ra.security.repository.IDiscountRepsository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.DiscountMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountService implements IGenericService<DiscountResponse, DiscountRequest, Long> {
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private IDiscountRepsository discountRepsository;

    @Override
    public List<DiscountResponse> findAll() {
        return discountRepsository.findAll().stream()
                .map(d -> discountMapper.toResponse(d)).collect(Collectors.toList());
    }

    @Override
    public DiscountResponse findById(Long aLong) throws CategoryException, ColorException, OrderException, DiscountException {
        Optional<Discount> discount = discountRepsository.findById(aLong);
        return discount.map(item -> discountMapper.toResponse(item)).orElseThrow(() ->
                new DiscountException(("Discount not found")));
    }

    @Override
    public DiscountResponse save(DiscountRequest discountRequest) throws CategoryException, BrandException, ColorException, DiscountException {
        if (discountRepsository.existsByName(discountRequest.getName())) {
            throw new DiscountException("Discount already exists");
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
    public DiscountResponse delete(Long aLong) {
        Optional<Discount> discount = discountRepsository.findById(aLong);
        if (discount.isPresent()) {
            discountRepsository.deleteById(aLong);
            return  discountMapper.toResponse(discount.get());
        }
        return null;
    }
}
