package com.alexandre.orderservice.service.order;

import com.alexandre.orderservice.dto.order.OrderDTO;
import com.alexandre.orderservice.dto.orderItem.OrderItemDTO;
import com.alexandre.orderservice.service.CrudService;
import jakarta.validation.Valid;

import java.util.UUID;

public interface OrderService extends CrudService<OrderDTO, UUID> {
    Boolean profileOwnsOrder(UUID orderId, UUID profileId);
    void addItemToOrder(UUID orderId, OrderItemDTO orderItemDTO);
    void removeItemFromOrder(UUID orderId, UUID orderItemId);
}
