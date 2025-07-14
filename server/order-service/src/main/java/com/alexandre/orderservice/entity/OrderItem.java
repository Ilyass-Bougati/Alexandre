package com.alexandre.orderservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/*
    Note here that we're taking snapshots of the unit price and the product name
    at the time of ordering, so that further updates won't change what the user
    has ordered
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    @NotNull
    private UUID productVariationId;

    @NotNull
    private Integer quantity;

    @NotEmpty
    private String productName;

    @NotNull
    private BigDecimal unitPriceAtOrderTime;
}
