package com.alexandre.orderservice.dto.order;

import com.alexandre.orderservice.dto.orderItem.OrderItemMapper;
import com.alexandre.orderservice.entity.Order;
import com.alexandre.orderservice.service.orderItem.OrderItemEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class OrderMapperImpl implements OrderMapper {

    private final OrderItemMapper orderItemMapper;
    private final OrderItemEntityService orderItemEntityService;

    @Override
    public OrderDTO toDto(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .profileId(order.getProfileId())
                .addressId(order.getAddressId())
                .state(order.getState())
                .createdAt(order.getCreatedAt())
                // This might not work
                .items(order.getItems().stream().map(orderItemMapper::toDto).toList())
                .build();
    }

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        return Order.builder()
                .profileId(orderDTO.getProfileId())
                .addressId(orderDTO.getAddressId())
                .state(orderDTO.getState())
                .createdAt(orderDTO.getCreatedAt())
                // This might not work
                .items(orderDTO.getItems().stream()
                        .map(item -> orderItemEntityService.findById(item.getId()))
                        .toList()
                ).build();
    }
}
