package com.alexandre.orderservice.service.implementation;

import com.alexandre.orderservice.dto.OrderDTO;
import com.alexandre.orderservice.entity.Order;
import com.alexandre.orderservice.mapper.OrderMapper;
import com.alexandre.orderservice.repository.OrderRepository;
import com.alexandre.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO getOrderById(UUID id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderRepository.save(orderMapper.toOrder(orderDTO));
        return orderMapper.toOrderDTO(order);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }
}
