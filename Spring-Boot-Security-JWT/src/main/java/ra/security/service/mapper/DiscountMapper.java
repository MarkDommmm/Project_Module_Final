package ra.security.service.mapper;

import org.springframework.stereotype.Component;
import ra.security.model.domain.Discount;
import ra.security.model.dto.request.DiscountRequest;
import ra.security.model.dto.response.DiscountResponse;
import ra.security.service.IGenericMapper;

@Component
public class DiscountMapper  implements IGenericMapper<Discount, DiscountRequest, DiscountResponse> {


    @Override
    public Discount toEntity(DiscountRequest discountRequest) {
        return Discount.builder()
                .name(discountRequest.getName())
                .description(discountRequest.getDescription())
                .startDate(discountRequest.getStartDate())
                .endDate(discountRequest.getEndDate())
                .require_price(discountRequest.getRequire_price())
                .promotion_price(discountRequest.getPromotion_price())
                .stock(discountRequest.getStock())
                .build();
    }

    @Override
    public DiscountResponse toResponse(Discount discount) {
        return DiscountResponse.builder()
                .id(discount.getId())
                .name(discount.getName())
                .description(discount.getDescription())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .require_price(discount.getRequire_price())
                .promotion_price(discount.getPromotion_price())
                .stock(discount.getStock())
                .build();
    }
}
