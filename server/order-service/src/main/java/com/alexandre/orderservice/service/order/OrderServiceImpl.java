package com.alexandre.orderservice.service.order;

import com.alexandre.orderservice.dto.ProductDTO;
import com.alexandre.orderservice.dto.ProductVariationDTO;
import com.alexandre.orderservice.dto.order.OrderDTO;
import com.alexandre.orderservice.dto.orderItem.OrderItemDTO;
import com.alexandre.orderservice.dto.orderItem.OrderItemMapper;
import com.alexandre.orderservice.entity.Order;
import com.alexandre.orderservice.dto.order.OrderMapper;
import com.alexandre.orderservice.entity.OrderItem;
import com.alexandre.orderservice.enums.OrderState;
import com.alexandre.orderservice.event.NotificationEvent;
import com.alexandre.orderservice.exception.NotFoundException;
import com.alexandre.orderservice.repository.OrderRepository;
import com.alexandre.orderservice.service.product.ProductService;
import com.alexandre.orderservice.service.product.ProductVariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderEntityService orderEntityService;
    private final ProductService productService;
    private final ProductVariationService productVariationService;
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @Override
    public OrderDTO findById(UUID id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        // creating the Order
        Order order = orderMapper.toEntity(orderDTO);
        order.setItems(new ArrayList<>());
        Order savedOrder = orderRepository.save(order);

        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            ProductVariationDTO productVariationDTO = productVariationService.get(orderItemDTO.getProductVariationId());
            ProductDTO productDTO = productService.get(productVariationDTO.getProductId());

            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .productName(productDTO.getName())
                    .unitPriceAtOrderTime(productVariationDTO.getUnitPrice())
                    .productVariationId(orderItemDTO.getProductVariationId())
                    .quantity(orderItemDTO.getQuantity())
                    .build();

            savedOrder.getItems().add(orderItem);
        }

        return orderMapper.toDto(orderRepository.save(savedOrder));
    }

    /**
     * This isn't implemented yet
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }

    /**
     * This function checks if an order was created by a certain profile
     * @param orderId the order's id
     * @param profileId the profile's id
     * @return a boolean, true if the order is owned by the profile
     */
    @Override
    public Boolean profileOwnsOrder(UUID orderId, UUID profileId) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Order not found");
        }
        return orderRepository.existsByIdAndProfileId(orderId, profileId);
    }

    /**
     * This function adds an item to an already existing order
     * @param orderId the id of the order
     * @param orderItemDTO the item to add to the order
     */
    @Override
    public void addItemToOrder(UUID orderId, OrderItemDTO orderItemDTO) {
        // getting the order
        Order order = orderEntityService.findById(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }

        if (order.getState() != OrderState.PENDING) {
            throw new RuntimeException("Can't modify a confirmed order");
        }

        ProductVariationDTO productVariationDTO = productVariationService.get(orderItemDTO.getProductVariationId());
        ProductDTO productDTO = productService.get(productVariationDTO.getProductId());

        orderItemDTO.setProductName(productDTO.getName());
        orderItemDTO.setUnitPriceAtOrderTime(productVariationDTO.getUnitPrice());
        orderItemDTO.setOrderId(orderId);

        OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
        order.getItems().add(orderItem);
    }

    @Override
    public void removeItemFromOrder(UUID orderId, UUID orderItemId) {
        // getting the order
        Order order = orderEntityService.findById(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }

        if (order.getState() != OrderState.PENDING) {
            throw new RuntimeException("Can't modify a confirmed order");
        }

        if (order.getItems().stream().filter(orderItem -> orderItem.getId().equals(orderItemId)).findFirst().isEmpty()) {
            throw new NotFoundException("Order item not found");
        }

        order.getItems().removeIf(orderItem -> orderItem.getId().equals(orderItemId));
    }
}
