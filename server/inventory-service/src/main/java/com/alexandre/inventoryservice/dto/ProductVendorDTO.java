package com.alexandre.inventoryservice.dto;

import com.alexandre.inventoryservice.entity.Product;
import com.alexandre.inventoryservice.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVendorDTO {
    private UUID id;

    @NotNull(message = "The purchase price can't be null")
    @Column(nullable = false)
    @Min(value = 0, message = "The price can't be negative")
    private BigDecimal purchasePrice;

    @NotNull(message = "The delay days (delivery delay in days) can't be null")
    @Column(nullable = false)
    @Min(value = 0, message = "The price can't be negative")
    private Integer deliveryDelayDays;

    @NotNull(message = "The product id can't be null")
    private UUID productId;

    @NotNull(message = "The vendor id can't be null")
    private UUID vendorId;
}
