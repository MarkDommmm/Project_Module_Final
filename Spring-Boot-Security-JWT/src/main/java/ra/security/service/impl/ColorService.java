package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.model.domain.Color;
import ra.security.model.dto.request.ColorRequest;
import ra.security.model.dto.response.ColorResponse;
import ra.security.repository.IColorRepository;
import ra.security.service.IGenericService;

import java.util.List;
import java.util.Optional;

@Service
public class ColorService implements IGenericService<ColorResponse, ColorRequest, Long> {
    @Autowired
    private IColorRepository colorRepository;


    @Override
    public List<ColorResponse> findAll() {
        return null;
    }

    @Override
    public ColorResponse findById(Long aLong) {
        return null;
    }

    @Override
    public ColorResponse save(ColorRequest colorRequest) {
        return null;
    }

    @Override
    public ColorResponse update(ColorRequest colorRequest, Long id) {
        return null;
    }

    @Override
    public ColorResponse delete(Long aLong) {
        return null;
    }
}
