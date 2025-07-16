package com.alexandre.entity;

import com.alexandre.enums.TransactionState;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private UUID orderId;

    @NotEmpty
    private String stripeSessionId;

    @Min(0)
    private Long amount;

    @NotNull
    @Builder.Default
    private TransactionState state = TransactionState.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
