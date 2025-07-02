package com.alexandre.orderservice.service.order;

import com.alexandre.orderservice.entity.Order;
import com.alexandre.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderEntityServiceImpl implements OrderEntityService {

    private final OrderRepository orderRepository;

    @Override
    public Order findById(UUID uuid) {
        return orderRepository.findById(uuid)
                .orElse(null);
    }
}
