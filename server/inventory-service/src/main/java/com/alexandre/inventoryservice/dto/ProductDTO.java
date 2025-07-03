package com.alexandre.inventoryservice.dto;

import com.alexandre.inventoryservice.entity.ProductImage;
import com.alexandre.inventoryservice.entity.ProductVariation;
import com.alexandre.inventoryservice.entity.ProductVendor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class ProductDTO {
    private UUID id;

    @NotEmpty(message = "The name can't be empty")
    private String name;

    @NotEmpty(message = "The description can't be empty")
    private String description;

    @Builder.Default
    private List<UUID> productImagesId = new ArrayList<>();
}
