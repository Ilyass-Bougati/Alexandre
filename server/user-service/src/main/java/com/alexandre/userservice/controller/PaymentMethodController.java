package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.PaymentMethodDTO;
import com.alexandre.userservice.record.UserPrincipal;
import com.alexandre.userservice.service.paymentMethod.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/paymentMethod/api/v1")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @PreAuthorize("@paymentMethodService.profileOwnsPaymentMethod(#userPrincipal.profile.id, #id)")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethod(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        return ResponseEntity.ok(paymentMethodService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentMethodDTO>> getAllPaymentMethodes(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(paymentMethodService.findByProfileId(userPrincipal.profile().getId()));
    }

    @PostMapping("/")
    public ResponseEntity<PaymentMethodDTO> createPaymentMethod(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid PaymentMethodDTO paymentMethodDTO) {
        paymentMethodDTO.setProfileId(userPrincipal.profile().getId());
        return ResponseEntity.ok(paymentMethodService.create(paymentMethodDTO));
    }

    @PreAuthorize("@paymentMethodService.profileOwnsPaymentMethod(#userPrincipal.profile.id, #paymentMethodDTO.id)")
    @PutMapping("/")
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid PaymentMethodDTO paymentMethodDTO) {
        paymentMethodDTO.setProfileId(userPrincipal.profile().getId());
        return ResponseEntity.ok(paymentMethodService.update(paymentMethodDTO));
    }

    @PreAuthorize("@paymentMethodService.profileOwnsPaymentMethod(#userPrincipal.profile.id, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        paymentMethodService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
