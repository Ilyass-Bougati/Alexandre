package com.alexandre.inventoryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_vendor")
public class ProductVendor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "The purchase price can't be null")
    @Column(nullable = false)
    @Min(value = 0, message = "The price can't be negative")
    private BigDecimal purchasePrice;

    @NotNull(message = "The delay days (delivery delay in days) can't be null")
    @Column(nullable = false)
    @Min(value = 0, message = "The price can't be negative")
    private Integer deliveryDelayDays;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vendor_id", nullable=false)
    private Vendor vendor;
}
