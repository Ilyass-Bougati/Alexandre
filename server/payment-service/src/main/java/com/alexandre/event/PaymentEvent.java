package com.alexandre.event;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
    UUID transactionId;
    UUID profileId;
    String email;
    LocalDateTime timestamp;
}
