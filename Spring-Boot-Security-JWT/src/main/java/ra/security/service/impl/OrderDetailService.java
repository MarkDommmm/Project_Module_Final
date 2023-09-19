package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.*;
import ra.security.model.domain.OrderDetails;
import ra.security.model.dto.request.OrderDetailsRequest;
import ra.security.model.dto.response.OrderDetailsResponse;
import ra.security.repository.OrderDetailRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.OrderDetailsMapper;
import ra.security.service.mapper.OrderMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailService implements IGenericService<OrderDetailsResponse, OrderDetailsRequest, Long> {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailsMapper orderMapper;

    @Override
    public List<OrderDetailsResponse> findAll() {
        return orderDetailRepository.findAll().stream()
                .map(o -> orderMapper.toResponse(o)).collect(Collectors.toList());
    }

    @Override
    public OrderDetailsResponse findById(Long aLong) throws  CustomException {
        Optional<OrderDetails> orderDetails = orderDetailRepository.findById(aLong);

        return orderDetails.map(o -> orderMapper.toResponse(o)).orElseThrow(()->
                new CustomException("OrderDetails not found"));
    }

    @Override
    public OrderDetailsResponse save(OrderDetailsRequest orderDetailsRequest) {
        return orderMapper.toResponse(orderDetailRepository.save(orderMapper.toEntity(orderDetailsRequest)));
    }

    @Override
    public OrderDetailsResponse update(OrderDetailsRequest orderDetailsRequest, Long id) {
        OrderDetails orderDetails = orderMapper.toEntity(orderDetailsRequest);
        orderDetails.setId(id);
        return orderMapper.toResponse(orderDetailRepository.save(orderDetails));
    }

    @Override
    public OrderDetailsResponse delete(Long aLong) {
        return null;
    }
}
