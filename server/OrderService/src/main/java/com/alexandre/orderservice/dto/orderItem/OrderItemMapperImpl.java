package com.alexandre.orderservice.dto.orderItem;

import com.alexandre.orderservice.entity.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderItemMapperImpl implements OrderItemMapper {
    @Override
    public OrderItem toEntity(OrderItemDTO orderItemDTO) {
        return OrderItem.builder()
                .quantity(orderItemDTO.getQuantity())
                .productId(orderItemDTO.getProductId())
                .productName(orderItemDTO.getProductName())
                .unitPriceAtOrderTime(orderItemDTO.getUnitPriceAtOrderTime())
                // TODO : Add order
                .build();
    }

    @Override
    public OrderItemDTO toDto(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .quantity(orderItem.getQuantity())
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .unitPriceAtOrderTime(orderItem.getUnitPriceAtOrderTime())
                .productId(orderItem.getProductId())
                .orderId(orderItem.getId())
                .id(orderItem.getId())
                .build();
    }
}
