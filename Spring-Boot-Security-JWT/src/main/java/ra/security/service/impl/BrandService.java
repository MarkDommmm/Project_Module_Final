package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.BrandException;
import ra.security.exception.CategoryException;
import ra.security.model.domain.Brand;
import ra.security.model.domain.Category;
import ra.security.model.domain.Product;
import ra.security.model.dto.request.BrandRequest;
import ra.security.model.dto.response.BrandResponse;
import ra.security.repository.IBrandRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.BrandMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService implements IGenericService<BrandResponse, BrandRequest, Long> {

    @Autowired
    private IBrandRepository brandRepository;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<BrandResponse> findAll() {
        return brandRepository.findAll().stream()
                .map(c -> brandMapper.toResponse(c)).collect(Collectors.toList());
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
    public BrandResponse save(BrandRequest brandRequest) throws BrandException {
        if (brandRepository.existsByName(brandRequest.getName())) {
            throw new BrandException("Brand already exists");
        }
        return brandMapper.toResponse(brandRepository.save(brandMapper.toEntity(brandRequest)));
    }

    @Override
    public BrandResponse update(BrandRequest brandRequest, Long id) {
        Brand brand = brandMapper.toEntity(brandRequest);
        brand.setId(id);
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @Override
    public BrandResponse delete(Long aLong) {
        Optional<Brand> brand = brandRepository.findById(aLong);
        if (brand.isPresent()) {
            brandRepository.deleteById(aLong);
            return brandMapper.toResponse(brand.get());
        }
        return null;
    }

    public Brand findBrandById(Long id) throws CategoryException {
        Optional<Brand> brand = brandRepository.findById(id);
        return brand.orElseThrow(() -> new CategoryException("Brand not found"));
    }
}
