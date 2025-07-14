package com.alexandre.orderservice.dto;

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
public class ProductDTO {
    private UUID id;

    @NotEmpty(message = "The name can't be empty")
    private String name;

    @NotEmpty(message = "The description can't be empty")
    private String description;

    @Builder.Default
    private List<UUID> productImagesId = new ArrayList<>();
}
