package ra.security.service.mapper;

import org.springframework.stereotype.Component;
import ra.security.model.domain.ImageProduct;
import ra.security.model.dto.request.ImageProductRequest;
import ra.security.model.dto.response.ImageProductResponse;
import ra.security.service.IGenericMapper;

@Component
public class ImageProductMapper implements IGenericMapper<ImageProduct, ImageProductRequest, ImageProductResponse> {
    @Override
    public ImageProduct toEntity(ImageProductRequest imageProductRequest) {
        return ImageProduct.builder()
                .image(imageProductRequest.getImage())
                .product(imageProductRequest.getProduct())
                .build();
    }

    @Override
    public ImageProductResponse toResponse(ImageProduct imageProduct) {
        return ImageProductResponse.builder()
                .id(imageProduct.getId())
                .image(imageProduct.getImage())
                .product(imageProduct.getProduct())
                .build();
    }
}
