package com.alexandre.orderservice.service.orderItem;

import com.alexandre.orderservice.dto.orderItem.OrderItemDTO;
import com.alexandre.orderservice.service.CrudService;

import java.util.List;
import java.util.UUID;

public interface OrderItemService extends CrudService<OrderItemDTO, UUID> {
    List<OrderItemDTO> getOrdersItems(UUID orderId);
}
