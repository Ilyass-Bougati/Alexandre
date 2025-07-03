package com.alexandre.inventoryservice.dto;


import com.alexandre.inventoryservice.entity.ProductVariation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {
    private UUID id;

    @NotEmpty(message = "The name can't be null or empty")
    private String name;

    @NotEmpty(message = "The location can't be null or empty")
    private String location;

    @Builder.Default
    private List<UUID> productVariationsId = new ArrayList<>();
}
