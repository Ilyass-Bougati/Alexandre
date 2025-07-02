package com.alexandre.orderservice.service.order;

import com.alexandre.orderservice.dto.order.OrderDTO;
import com.alexandre.orderservice.dto.orderItem.OrderItemMapper;
import com.alexandre.orderservice.entity.Order;
import com.alexandre.orderservice.dto.order.OrderMapper;
import com.alexandre.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDTO findById(UUID id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        // creating the Order
        Order order = orderMapper.toEntity(orderDTO);
        order.setItems(new ArrayList<>());
        Order savedOrder = orderRepository.save(order);

        // TODO : Get the products data from the InventoryService

        // Inserting the items
        savedOrder.setItems(orderDTO.getItems().stream().map(orderItemMapper::toEntity).collect(Collectors.toCollection(ArrayList::new)));
        savedOrder.getItems().forEach(item -> item.setOrder(savedOrder));
        return orderMapper.toDto(orderRepository.save(order));
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
