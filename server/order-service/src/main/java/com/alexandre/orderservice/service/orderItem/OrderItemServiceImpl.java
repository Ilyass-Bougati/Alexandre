package com.alexandre.orderservice.service.orderItem;

import com.alexandre.orderservice.dto.orderItem.OrderItemDTO;
import com.alexandre.orderservice.dto.orderItem.OrderItemMapper;
import com.alexandre.orderservice.entity.Order;
import com.alexandre.orderservice.entity.OrderItem;
import com.alexandre.orderservice.exception.NotFoundException;
import com.alexandre.orderservice.repository.OrderItemRepository;
import com.alexandre.orderservice.service.order.OrderEntityService;
import com.alexandre.orderservice.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service("orderItemService")
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderService orderService;
    private final OrderEntityService orderEntityService;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<OrderItemDTO> getOrdersItems(UUID orderId) {
        return orderItemRepository.findOrderItemByOrderId(orderId)
                .stream().map(orderItemMapper::toDto).toList();
    }

    @Override
    public void addOrderItem(OrderItemDTO orderItemDTO, UUID orderId) {
        Order order = orderEntityService.findById(orderId);

        // if the order doesn't exist
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        orderItemDTO.setOrderId(order.getId());
        order.getItems().add(orderItemMapper.toEntity(orderItemDTO));
    }

    @Override
    public Boolean profileOwnsOrderItem(UUID orderItemId, UUID profileId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new NotFoundException("Order item not found");
        }
        return orderItemRepository.existsByIdAndOrder_ProfileId(orderItemId, profileId);
    }
}
