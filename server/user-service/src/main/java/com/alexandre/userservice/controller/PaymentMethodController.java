package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.PaymentMethodDTO;
import com.alexandre.userservice.service.paymentMethod.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/paymentmethod/api/v1")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethod(@PathVariable UUID id) {
        return ResponseEntity.ok(paymentMethodService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PaymentMethodDTO> createPaymentMethod(@RequestBody @Valid PaymentMethodDTO paymentMethodDTO) {
        return ResponseEntity.ok(paymentMethodService.create(paymentMethodDTO));
    }

    @PutMapping
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(@RequestBody @Valid PaymentMethodDTO paymentMethodDTO) {
        return ResponseEntity.ok(paymentMethodService.update(paymentMethodDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable UUID id) {
        paymentMethodService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
