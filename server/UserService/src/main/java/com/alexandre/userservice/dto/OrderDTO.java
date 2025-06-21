package com.alexandre.userservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UUID id;
    @NotNull
    private UUID userId;
    @NotNull
    private UUID productId;
}
