package com.alexandre.orderservice.mapper.implementation;

import com.alexandre.orderservice.dto.OrderDTO;
import com.alexandre.orderservice.entity.Order;
import com.alexandre.orderservice.mapper.OrderMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderMapperImpl implements OrderMapper {
    @Override
    public OrderDTO toOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .userId(order.getUserId())
                .build();
    }

    @Override
    public Order toOrder(OrderDTO orderDTO) {
        return Order.builder()
                .userId(orderDTO.getUserId())
                .productId(orderDTO.getProductId())
                .build();
    }
}
