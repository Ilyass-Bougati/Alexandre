package com.alexandre.orderservice.controller;

import com.alexandre.orderservice.dto.order.OrderDTO;
import com.alexandre.orderservice.exception.NotFoundException;
import com.alexandre.orderservice.record.UserPrincipal;
import com.alexandre.orderservice.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("@orderService.profileOwnsOrder(#orderDTO.id, #userPrincipal.profile.id)")
    @PutMapping("/")
    public ResponseEntity<OrderDTO> updateOrder(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.update(orderDTO));
    }

    @PreAuthorize("@orderService.profileOwnsOrder(#id, #userPrincipal.profile.id)")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PreAuthorize("@orderService.profileOwnsOrder(#id, #userPrincipal.profile.id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        orderService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
