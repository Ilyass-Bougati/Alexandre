package com.alexandre.orderservice.service.order;

import com.alexandre.orderservice.dto.order.OrderDTO;
import com.alexandre.orderservice.entity.Order;
import com.alexandre.orderservice.dto.order.OrderMapper;
import com.alexandre.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO findById(UUID id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        Order order = orderRepository.save(orderMapper.toEntity(orderDTO));
        return orderMapper.toDto(order);
    }

    /**
     * This isn't implemented yet
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }
}
