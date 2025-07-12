package com.alexandre.orderservice.dto.orderItem;

import com.alexandre.orderservice.entity.OrderItem;
import com.alexandre.orderservice.service.order.OrderEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemMapperImpl implements OrderItemMapper {

    private final OrderEntityService orderEntityService;

    @Override
    public OrderItem toEntity(OrderItemDTO orderItemDTO) {
        return OrderItem.builder()
                .quantity(orderItemDTO.getQuantity())
                .productVariationId(orderItemDTO.getProductVariationId())
                .productName(orderItemDTO.getProductName())
                .unitPriceAtOrderTime(orderItemDTO.getUnitPriceAtOrderTime())
                .order(orderEntityService.findById(orderItemDTO.getOrderId()))
                .build();
    }

    @Override
    public OrderItemDTO toDto(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .quantity(orderItem.getQuantity())
                .productVariationId(orderItem.getProductVariationId())
                .productName(orderItem.getProductName())
                .unitPriceAtOrderTime(orderItem.getUnitPriceAtOrderTime())
                .productVariationId(orderItem.getProductVariationId())
                .orderId(orderItem.getId())
                .id(orderItem.getId())
                .build();
    }
}
