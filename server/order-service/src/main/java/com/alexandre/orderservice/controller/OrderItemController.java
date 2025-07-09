package com.alexandre.orderservice.controller;

import com.alexandre.orderservice.dto.orderItem.OrderItemDTO;
import com.alexandre.orderservice.record.UserPrincipal;
import com.alexandre.orderservice.service.order.OrderService;
import com.alexandre.orderservice.service.orderItem.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item/api/v1")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;

    @PreAuthorize("@orderItemService.profileOwnsOrderItem(#orderItemId, #userPrincipal.profile.id)")
    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItemDTO> getOrderItem(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID orderItemId) {
        return ResponseEntity.ok(orderItemService.findById(orderItemId));
    }

    @PreAuthorize("@orderService.profileOwnsOrder(#orderId, #userPrincipal.profile.id)")
    @PostMapping("/{orderId}")
    public ResponseEntity<Void> addItemToOrder(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID orderId, @RequestBody @Valid OrderItemDTO orderItemDTO) {
        orderItemService.addOrderItem(orderItemDTO, orderId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@orderService.profileOwnsOrder(#orderItemDTO.orderId, #userPrincipal.profile.id)")
    @PutMapping("/")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid OrderItemDTO orderItemDTO) {
        return ResponseEntity.ok(orderItemService.update(orderItemDTO));
    }

    @PreAuthorize("@orderItemService.profileOwnsOrderItem(#orderItemId, #userPrincipal.profile.id)")
    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItem(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID orderItemId) {
        orderItemService.deleteById(orderItemId);
        return ResponseEntity.ok().build();
    }
}
