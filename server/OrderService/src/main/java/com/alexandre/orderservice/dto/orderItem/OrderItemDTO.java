package com.alexandre.orderservice.dto.orderItem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private UUID id;
    private UUID orderId;

    @NotNull
    private UUID productId;

    @NotNull
    private Integer quantity;

    private String productName;
    private Double unitPriceAtOrderTime;
}
