package com.alexandre.orderservice.mapper;

import com.alexandre.orderservice.dto.OrderDTO;
import com.alexandre.orderservice.entity.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    OrderDTO toOrderDTO(Order order);
    Order toOrder(OrderDTO orderDTO);
}
