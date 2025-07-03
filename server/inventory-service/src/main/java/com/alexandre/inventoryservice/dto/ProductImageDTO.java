package com.alexandre.inventoryservice.dto;

import com.alexandre.inventoryservice.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO {
    private UUID id;

    @NotEmpty(message = "The uri can't be empty")
    private String uri;

    @NotNull(message = "The order index can't be null")
    @Min(value = 0, message = "The order index can't be negative")
    private Integer orderIndex;

    @Builder.Default
    private Boolean thumbnail = false;

    @NotNull(message = "The product Id can't be null")
    private UUID productId;
}
