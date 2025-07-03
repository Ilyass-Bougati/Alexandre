package com.alexandre.inventoryservice.dto;

import com.alexandre.inventoryservice.entity.Product;
import com.alexandre.inventoryservice.entity.VariationImage;
import com.alexandre.inventoryservice.entity.Warehouse;
import com.alexandre.inventoryservice.enums.VariationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationDTO {
    private UUID id;

    @NotEmpty(message = "The sku can't be empty or null")
    private String sku;

    @Min(value = 0, message = "The available quantity can't be negative")
    private Integer availableQuantity;

    @Min(value = 0, message = "The inventory quantity can't be negative")
    private Integer inventoryQuantity;

    @NotNull(message = "The unit price can't be null")
    @Min(value = 0, message = "The unit price can't be negative")
    private BigDecimal unitPrice;

    @NotNull(message = "The variation type can't be null")
    @Enumerated(EnumType.STRING)
    private VariationType type;

    @Builder.Default
    private Boolean defaultVariation = false;

    @NotNull(message = "The product id can't be null")
    private UUID productId;

    @Builder.Default
    private List<UUID> variationImages = new ArrayList<>();

    @NotNull(message = "The warehouse id can't be null")
    private UUID warehouseId;
}
