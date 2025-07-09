package com.alexandre.orderservice.dto.orderItem;

import com.alexandre.orderservice.entity.OrderItem;
import com.alexandre.orderservice.repository.OrderItemRepository;
import com.alexandre.orderservice.service.order.OrderEntityService;
import com.alexandre.orderservice.service.order.OrderService;
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
                .productId(orderItemDTO.getProductId())
                .productName(orderItemDTO.getProductName())
                .unitPriceAtOrderTime(orderItemDTO.getUnitPriceAtOrderTime())
                .order(orderEntityService.findById(orderItemDTO.getOrderId()))
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
