package com.alexandre.orderservice.controller;

import com.alexandre.orderservice.dto.order.OrderDTO;
import com.alexandre.orderservice.exception.NotFoundException;
import com.alexandre.orderservice.record.UserPrincipal;
import com.alexandre.orderservice.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order/api/v1")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<OrderDTO> createOrder(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid OrderDTO orderDTO) {
        orderDTO.setProfileId(userPrincipal.profile().getId());
        return ResponseEntity.ok(orderService.create(orderDTO));
    }

    @PutMapping("/")
    public ResponseEntity<OrderDTO> updateOrder(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid OrderDTO orderDTO) {
        if (orderService.profileOwnsOrder(orderDTO.getId(), userPrincipal.profile().getId())) {
            return ResponseEntity.ok(orderService.update(orderDTO));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        if (orderService.profileOwnsOrder(id, userPrincipal.profile().getId())) {
            return ResponseEntity.ok(orderService.findById(id));
        } else {
            throw new NotFoundException("Order not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        if (orderService.profileOwnsOrder(id, userPrincipal.profile().getId())) {
            orderService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            throw new NotFoundException("Order not found");
        }
    }
}
