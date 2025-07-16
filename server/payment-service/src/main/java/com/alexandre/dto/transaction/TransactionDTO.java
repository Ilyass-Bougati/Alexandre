package com.alexandre.dto.transaction;

import com.alexandre.enums.TransactionState;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private UUID id;

    @NotNull
    private UUID orderId;

    @Min(0)
    private Long amount;

    private String stripeSessionId;

    @Builder.Default
    private TransactionState state = TransactionState.PENDING;

    private LocalDateTime createdAt;
}
