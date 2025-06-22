package com.alexandre.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    private String stripeToken;

    @NotBlank
    private String last4Digits;

    @NotNull
    private UUID profileId;
}
