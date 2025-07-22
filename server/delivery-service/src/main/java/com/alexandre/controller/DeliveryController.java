package com.alexandre.controller;

import com.alexandre.dto.delivery.DeliveryDTO;
import com.alexandre.entity.Delivery;
import com.alexandre.service.delivery.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery/api/v1")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(deliveryService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<DeliveryDTO>> findAllById() {
        return ResponseEntity.ok(deliveryService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<DeliveryDTO> create(@RequestBody @Valid DeliveryDTO deliveryDTO) {
        return ResponseEntity.ok(deliveryService.create(deliveryDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable UUID id) {
        deliveryService.delete(id);
    }
}
