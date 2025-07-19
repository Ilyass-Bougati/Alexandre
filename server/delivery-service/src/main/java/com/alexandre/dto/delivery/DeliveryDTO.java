package com.alexandre.dto.delivery;

import com.alexandre.enums.DeliveryState;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {
    private UUID id;

    @NotBlank
    private String recipientFirstName;

    @NotBlank
    private String recipientLastName;

    @NotBlank
    @Email
    private String recipientEmail;

    @NotBlank
    private String recipientPhone;

    @NotBlank
    private String productName;

    @NotNull
    @Min(value = 0)
    private Long productPrice;

    @NotNull
    private Boolean prePaid;

    @NotBlank
    private String street;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String notes;

    @NotNull
    private DeliveryState state;

    @NotNull
    private UUID cityId;

    @NotNull
    private UUID companyId;

    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
}
