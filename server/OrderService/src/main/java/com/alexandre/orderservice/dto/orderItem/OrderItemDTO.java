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
    @NotNull
    private UUID orderId;

    @NotNull
    private UUID productId;

    @NotNull
    private Integer quantity;

    @NotEmpty
    private String productName;

    @NotNull
    private Double unitPriceAtOrderTime;
}
