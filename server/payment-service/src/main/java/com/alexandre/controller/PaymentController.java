package com.alexandre.controller;

import com.alexandre.dto.StripeResponse;
import com.alexandre.enums.TransactionState;
import com.alexandre.record.UserPrincipal;
import com.alexandre.service.payment.PaymentService;
import com.alexandre.service.stripe.StripeServiceImpl;
import com.alexandre.service.transaction.TransactionService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment/api/v1/")
public class PaymentController {

    private final StripeServiceImpl stripeServiceImpl;
    private final PaymentService paymentService;

    @PreAuthorize("@orderService.checkProfileOwnsOrder(#userPrincipal.profile.id, #orderId, #userPrincipal.token)")
    @PostMapping("/pay/{orderId}")
    public ResponseEntity<StripeResponse> pay(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID orderId) throws StripeException {
        return ResponseEntity.ok(stripeServiceImpl.checkout(orderId, userPrincipal.token()));
    }

    @PreAuthorize("@transactionService.checkProfileOwnsTransaction(#userPrincipal.profile.id, #transactionId, #userPrincipal.token)")
    @GetMapping("/success/{transactionId}")
    public ResponseEntity<Void> success(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID transactionId) throws StripeException {
        paymentService.successPayment(transactionId, userPrincipal.token());
        return ResponseEntity.status(HttpStatus.FOUND)
                // TODO : Change this later
                .location(URI.create("https://example.com"))
                .build();
    }

    @PreAuthorize("@transactionService.checkProfileOwnsTransaction(#userPrincipal.profile.id, #transactionId, #userPrincipal.token)")
    @GetMapping("/cancel/{transactionId}")
    public ResponseEntity<Void> cancel(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID transactionId) throws StripeException {
        paymentService.cancelPayment(transactionId, userPrincipal.token());
        return ResponseEntity.status(HttpStatus.FOUND)
                // TODO : Change this later
                .location(URI.create("https://example.com"))
                .build();
    }
}
