package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.*;
import ra.security.model.domain.Discount;
import ra.security.model.domain.ImageProduct;
import ra.security.model.dto.request.ImageProductRequest;
import ra.security.model.dto.response.ImageProductResponse;
import ra.security.repository.IImageProductRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.ImageProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageProductService implements IGenericService<ImageProductResponse, ImageProductRequest,Long> {
    @Autowired
    private IImageProductRepository imageProductRepository;
    @Autowired
    private ImageProductMapper imageProductMapper;
    @Override
    public List<ImageProductResponse> findAll() {
        return imageProductRepository.findAll().stream()
                .map(d -> imageProductMapper.toResponse(d)).collect(Collectors.toList());
    }

    @Override
    public ImageProductResponse findById(Long aLong) throws CategoryException, ColorException, OrderException, DiscountException, OrderDetailException, ShipmentException, PaymentException {
        Optional<ImageProduct> img = imageProductRepository.findById(aLong);
        return img.map(item -> imageProductMapper.toResponse(item)).orElseThrow(() ->
                new DiscountException(("Discount not found")));
    }

    @Override
    public ImageProductResponse save(ImageProductRequest imageProductRequest) throws CategoryException, BrandException, ColorException, DiscountException, ShipmentException {
        if (imageProductRepository.existsByImage(imageProductRequest.getImage())) {
            throw new DiscountException("Discount already exists");
        }
        return imageProductMapper.toResponse(imageProductRepository.save(imageProductMapper.toEntity(imageProductRequest)));
    }

    @Override
    public ImageProductResponse update(ImageProductRequest imageProductRequest, Long id) {
        ImageProduct img = imageProductMapper.toEntity(imageProductRequest);
        img.setId(id);
        return imageProductMapper.toResponse(imageProductRepository.save(img));
    }

    @Override
    public ImageProductResponse delete(Long aLong) {
        Optional<ImageProduct> img = imageProductRepository.findById(aLong);
        if (img.isPresent()) {
            imageProductRepository.deleteById(aLong);
            return  imageProductMapper.toResponse(img.get());
        }
        return null;
    }
}
