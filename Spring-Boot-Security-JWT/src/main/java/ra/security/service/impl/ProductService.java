package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.model.domain.Products;
import ra.security.model.dto.request.ProductRequest;

import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IProductRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements IGenericService<ProductResponse, ProductRequest, Long> {

    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(p -> productMapper.toResponse(p)).collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(Long aLong) {
        Optional<Products> p = productRepository.findById(aLong);
        if (p.isPresent()) {
            return productMapper.toResponse(p.get());
        }
        return null;
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) {
        Products p = productRepository.save(productMapper.toEntity(productRequest));
        return productMapper.toResponse(p);
    }


    @Override
    public ProductResponse update(ProductRequest productRequest, Long id) {
        Products p = productMapper.toEntity(productRequest);
        p.setId(id);
        return productMapper.toResponse(productRepository.save(p));
    }

    @Override
    public ProductResponse delete(Long aLong) {
        Optional<Products> p = productRepository.findById(aLong);
        if (p.isPresent()) {
            productRepository.deleteById(aLong);
            return productMapper.toResponse(p.get());
        }
        return null;
    }
}


