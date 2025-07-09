package com.alexandre.orderservice.repository;

import com.alexandre.orderservice.dto.orderItem.OrderItemDTO;
import com.alexandre.orderservice.entity.Order;
import com.alexandre.orderservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findOrderItemByOrderId(UUID orderId);

    List<OrderItem> order(Order order);

    Boolean existsByIdAndOrder_ProfileId(UUID orderItemId, UUID profileId);
}
