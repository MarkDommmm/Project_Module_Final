package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.model.domain.Brand;
import ra.security.model.domain.Products;
import ra.security.model.dto.request.BrandRequest;
import ra.security.model.dto.response.BrandResponse;
import ra.security.repository.IBrandRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.BrandMapper;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService implements IGenericService<BrandResponse, BrandRequest, Long> {
    @Autowired
    private IBrandRepository brandRepository;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<BrandResponse> findAll() {
        return null;
    }

    @Override
    public BrandResponse findById(Long aLong) {
        Optional<Brand> b = brandRepository.findById(aLong);
        if (b.isPresent()) {
            return brandMapper.toResponse(b.get());
        }
        return null;
    }

    @Override
    public BrandResponse save(BrandRequest brandRequest) {
        return null;
    }

    @Override
    public BrandResponse update(BrandRequest brandRequest, Long id) {
        return null;
    }

    @Override
    public BrandResponse delete(Long aLong) {
        return null;
    }
}
