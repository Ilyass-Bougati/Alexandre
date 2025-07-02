package com.alexandre.orderservice.service.orderItem;

import com.alexandre.orderservice.dto.orderItem.OrderItemDTO;
import com.alexandre.orderservice.dto.orderItem.OrderItemMapper;
import com.alexandre.orderservice.entity.OrderItem;
import com.alexandre.orderservice.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItemDTO findById(UUID id) {
        return orderItemRepository.findById(id)
                .map(orderItemMapper::toDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public OrderItemDTO create(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.save(orderItemMapper.toEntity(orderItemDTO));
        return orderItemMapper.toDto(orderItem);
    }

    /**
     * This isn't implemented yet
     * @param orderItemDTO
     * @return
     */
    @Override
    public OrderItemDTO update(OrderItemDTO orderItemDTO) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public List<OrderItemDTO> getOrdersItems(UUID orderId) {
        return orderItemRepository.findOrderItemByOrderId(orderId)
                .stream().map(orderItemMapper::toDto).toList();
    }
}
