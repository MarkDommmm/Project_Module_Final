package ra.security.service.mapper;

import ra.security.model.domain.Color;
import ra.security.model.dto.request.ColorRequest;
import ra.security.model.dto.response.ColorResponse;
import ra.security.service.IGenericMapper;

public class ColorMapper implements IGenericMapper<Color, ColorRequest, ColorResponse> {
    @Override
    public Color toEntity(ColorRequest colorRequest) {
        return Color.builder()
                .name(colorRequest.getName())
                .build();
    }

    @Override
    public ColorResponse toResponse(Color color) {
        return ColorResponse.builder()
                .name(color.getName())
                .build();
    }
}
