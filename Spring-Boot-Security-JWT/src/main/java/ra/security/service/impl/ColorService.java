package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.CustomException;
import ra.security.model.domain.Color;
import ra.security.model.dto.request.ColorRequest;
import ra.security.model.dto.response.ColorResponse;
import ra.security.repository.IColorRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.ColorMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColorService implements IGenericService<ColorResponse, ColorRequest, Long> {
    @Autowired
    private IColorRepository colorRepository;
    @Autowired
    private ColorMapper colorMapper;


    @Override
    public List<ColorResponse> findAll() {
        return colorRepository.findAll().stream()
                .map(c -> colorMapper.toResponse(c)).collect(Collectors.toList());
    }

    @Override
    public ColorResponse findById(Long aLong) throws CustomException {
        Optional<Color> color = colorRepository.findById(aLong);
        return color.map(c -> colorMapper.toResponse(c)).orElseThrow(() ->
                new CustomException("Color not found"));
    }

    @Override
    public ColorResponse save(ColorRequest colorRequest) throws CustomException {
        if (colorRepository.existsByName(colorRequest.getName())) {
            throw new CustomException("Color already exists");
        }
        return colorMapper.toResponse(colorRepository.save(colorMapper.toEntity(colorRequest)));
    }

    @Override
    public ColorResponse update(ColorRequest colorRequest, Long id) throws CustomException {
        Optional<Color> check = colorRepository.findById(id);
        if (check.isPresent()) {
            Color color = colorMapper.toEntity(colorRequest);
            color.setId(id);
            return colorMapper.toResponse(colorRepository.save(color));
        }
        throw new CustomException("Color not found");

    }

    @Override
    public ColorResponse delete(Long aLong) {
        Optional<Color> color = colorRepository.findById(aLong);
        if (color.isPresent()) {
            colorRepository.deleteById(aLong);
            return colorMapper.toResponse(color.get());
        }
        return null;
    }

    public Color findColorById(Long id) throws CustomException {
        Optional<Color> color = colorRepository.findById(id);
        return color.orElseThrow(() -> new CustomException("Color not found"));
    }
}
