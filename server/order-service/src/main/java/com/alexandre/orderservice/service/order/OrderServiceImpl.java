package com.alexandre.orderservice.service.order;

import com.alexandre.orderservice.dto.ProductDTO;
import com.alexandre.orderservice.dto.ProductVariationDTO;
import com.alexandre.orderservice.dto.order.OrderDTO;
import com.alexandre.orderservice.dto.orderItem.OrderItemDTO;
import com.alexandre.orderservice.dto.orderItem.OrderItemMapper;
import com.alexandre.orderservice.entity.Order;
import com.alexandre.orderservice.dto.order.OrderMapper;
import com.alexandre.orderservice.entity.OrderItem;
import com.alexandre.orderservice.exception.NotFoundException;
import com.alexandre.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final WebClient.Builder webClientBuilder;

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

        // TODO : Get the products data from the InventoryService
        WebClient webClient = webClientBuilder.build();
        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            ResponseEntity<ProductVariationDTO> productVariationDTORes = webClient.get()
                    .uri("http://inventory-service/inventory/api/v1/productVariation/" + orderItemDTO.getProductVariationId())
                    .retrieve()
                    .toEntity(ProductVariationDTO.class)
                    .block();

            ProductVariationDTO productVariationDTO = productVariationDTORes.getBody();

            ResponseEntity<ProductDTO> res = webClient.get()
                    .uri("http://inventory-service/inventory/api/v1/product/" + productVariationDTO.getProductId())
                    .retrieve()
                    .toEntity(ProductDTO.class)
                    .block();

            ProductDTO productDTO = res.getBody();

            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .productName(productDTO.getName())
                    .unitPriceAtOrderTime(productVariationDTO.getUnitPrice())
                    .productVariationId(orderItemDTO.getProductVariationId())
                    .quantity(orderItemDTO.getQuantity())
                    .build();

            savedOrder.getItems().add(orderItem);
        }



        // Inserting the items
//        savedOrder.setItems(orderDTO.getItems().stream().map(orderItemMapper::toEntity).collect(Collectors.toCollection(ArrayList::new)));
//        savedOrder.getItems().forEach(item -> item.setOrder(savedOrder));
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
}
