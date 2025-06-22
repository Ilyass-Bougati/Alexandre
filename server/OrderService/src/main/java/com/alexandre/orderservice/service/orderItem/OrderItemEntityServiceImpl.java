package com.alexandre.orderservice.service.orderItem;

import com.alexandre.orderservice.entity.OrderItem;
import com.alexandre.orderservice.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderItemEntityServiceImpl implements OrderItemEntityService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem findById(UUID uuid) {
        return orderItemRepository.findById(uuid)
                .orElse(null);
    }
}
