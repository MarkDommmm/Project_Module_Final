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
        return null;
    }

    @Override
    public DiscountResponse toResponse(Discount discount) {
        return null;
    }
}
