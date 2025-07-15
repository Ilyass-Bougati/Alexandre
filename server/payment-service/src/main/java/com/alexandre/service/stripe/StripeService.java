package com.alexandre.service.stripe;

import com.alexandre.dto.StripeResponse;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public interface StripeService {
    StripeResponse checkout(UUID orderId, Jwt jwt);
}
