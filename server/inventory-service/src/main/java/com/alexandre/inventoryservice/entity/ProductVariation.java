package com.alexandre.inventoryservice.entity;

import com.alexandre.inventoryservice.enums.VariationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_variations")
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    /**
     * In case the variation type is physical the variation images will be used
     * instead of the product images
     */
    @JsonIgnore
    @OneToMany(mappedBy = "variation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VariationImage> variationImages;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="warehouse_id", nullable=false)
    private Warehouse warehouse;
}
