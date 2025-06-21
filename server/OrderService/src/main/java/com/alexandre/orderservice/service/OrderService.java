package com.alexandre.orderservice.service;

import com.alexandre.orderservice.dto.OrderDTO;

import java.util.UUID;

public interface OrderService {
    OrderDTO getOrderById(UUID id);
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(OrderDTO orderDTO);
    // there's no way this is staying here
    void deleteOrder(UUID id);
}
