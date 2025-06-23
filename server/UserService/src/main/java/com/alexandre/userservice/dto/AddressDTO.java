package com.alexandre.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressDTO {
    private UUID id;

    @NotBlank
    private String street;

    @NotBlank
    private String postalCode;

    @NotNull
    private Boolean isDefault;

    @NotNull
    private UUID profileId;

    @NotNull
    private UUID cityId;

    private LocalDateTime createdAt;
}
