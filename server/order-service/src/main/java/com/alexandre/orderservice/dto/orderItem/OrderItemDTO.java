package com.alexandre.orderservice.dto.orderItem;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
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
    private UUID productVariationId;

    @NotNull
    private Integer quantity;

    private String productName;
    private BigDecimal unitPriceAtOrderTime;
}
