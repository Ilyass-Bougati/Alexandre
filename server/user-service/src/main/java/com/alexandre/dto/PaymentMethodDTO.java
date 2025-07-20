package com.alexandre.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentMethodDTO {
    private UUID id;

    @NotBlank
    private String cardHolderName;

    @NotBlank
    private String cardName;

    @NotBlank
    private String last4Digits;

    private UUID profileId;

    private LocalDateTime createdAt;
}
