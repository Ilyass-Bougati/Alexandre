package com.alexandre.orderservice.dto.order;

import com.alexandre.orderservice.dto.orderItem.OrderItemDTO;
import com.alexandre.orderservice.enums.OrderState;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UUID id;

    private UUID profileId;

    @NotNull
    @Builder.Default
    private UUID addressId = UUID.randomUUID();

    @Builder.Default
    private OrderState state = OrderState.PENDING;

    @Builder.Default
    private List<OrderItemDTO> items = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;
}
