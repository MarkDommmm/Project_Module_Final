package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.CustomException;
import ra.security.model.domain.Brand;
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
    public BrandResponse findById(Long aLong) throws CustomException {
        Optional<Brand> b = brandRepository.findById(aLong);
        if (b.isPresent()) {
            return brandMapper.toResponse(b.get());
        }
        throw new CustomException("Brand not found");
    }

    @Override
    public BrandResponse save(BrandRequest brandRequest) throws CustomException {
        if (brandRepository.existsByName(brandRequest.getName())) {
            throw new CustomException("Brand already exists");
        }
        return brandMapper.toResponse(brandRepository.save(brandMapper.toEntity(brandRequest)));
    }

    @Override
    public BrandResponse update(BrandRequest brandRequest, Long id) throws CustomException {
        Optional<Brand> check = brandRepository.findById(id);
        if (check.isPresent()) {
            Brand brand = brandMapper.toEntity(brandRequest);
            brand.setId(id);
            return brandMapper.toResponse(brandRepository.save(brand));
        }
        throw new CustomException("Brand not found");
    }

    @Override
    public BrandResponse delete(Long aLong) throws CustomException {
        Optional<Brand> brand = brandRepository.findById(aLong);
        if (brand.isPresent()) {
            brandRepository.deleteById(aLong);
            return brandMapper.toResponse(brand.get());
        }
        throw new CustomException("Brand not found");
    }

    public Brand findBrandById(Long id) throws CustomException {
        Optional<Brand> brand = brandRepository.findById(id);
        return brand.orElseThrow(() -> new CustomException("Brand not found"));
    }
}
