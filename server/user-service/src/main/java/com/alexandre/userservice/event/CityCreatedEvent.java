package com.alexandre.userservice.event;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityCreatedEvent {
    private UUID id;
    @NotBlank
    private String name;
    @NotNull
    @Min(0)
    private Double shippingFee;
}
