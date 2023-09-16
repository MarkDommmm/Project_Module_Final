package ra.security.service.mapper;

import org.springframework.stereotype.Component;
import ra.security.model.domain.Brand;
import ra.security.model.dto.request.BrandRequest;
import ra.security.model.dto.response.BrandResponse;
import ra.security.service.IGenericMapper;

@Component
public class BrandMapper  implements IGenericMapper<Brand, BrandRequest, BrandResponse> {
    @Override
    public Brand toEntity(BrandRequest brandRequest) {
        return Brand.builder()
                .name(brandRequest.getName())
                .build();
    }

    @Override
    public BrandResponse toResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
