package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.PaymentMethodDTO;
import com.alexandre.userservice.record.UserPrincipal;
import com.alexandre.userservice.service.paymentMethod.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/paymentMethod/api/v1")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethod(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        if (paymentMethodService.profileOwnsPaymentMethod(userPrincipal.profile().getId(), id)) {
            return ResponseEntity.ok(paymentMethodService.findById(id));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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

    @PutMapping("/")
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid PaymentMethodDTO paymentMethodDTO) {
        if (paymentMethodService.profileOwnsPaymentMethod(userPrincipal.profile().getId(), paymentMethodDTO.getId())) {
            paymentMethodDTO.setProfileId(userPrincipal.profile().getId());
            return ResponseEntity.ok(paymentMethodService.update(paymentMethodDTO));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        if (paymentMethodService.profileOwnsPaymentMethod(userPrincipal.profile().getId(), id)) {
            paymentMethodService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
