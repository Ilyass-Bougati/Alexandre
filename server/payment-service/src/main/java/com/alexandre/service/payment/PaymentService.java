package com.alexandre.service.payment;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public interface PaymentService {
    void successPayment(UUID transactionId, Jwt jwt);
    void cancelPayment(UUID transactionId, Jwt jwt);
}
