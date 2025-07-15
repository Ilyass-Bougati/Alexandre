package com.alexandre.controller;

import com.alexandre.dto.StripeResponse;
import com.alexandre.record.UserPrincipal;
import com.alexandre.service.stripe.StripeServiceImpl;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment/api/v1/")
public class PaymentController {

    private final StripeServiceImpl stripeServiceImpl;

    @PreAuthorize("@orderService.checkProfileOwnsOrder(#userPrincipal.profile.id, #orderId, #userPrincipal.token)")
    @PostMapping("/pay/{orderId}")
    public ResponseEntity<StripeResponse> pay(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID orderId) throws StripeException {
        return ResponseEntity.ok(stripeServiceImpl.checkout(orderId, userPrincipal.token()));
    }
}
