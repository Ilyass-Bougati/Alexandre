package com.alexandre.controller;

import com.alexandre.dto.deliveryCompany.DeliveryCompanyDTO;
import com.alexandre.service.deliveryCompany.DeliveryCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery-company/api/v1")
public class DeliveryCompanyController {

    private final DeliveryCompanyService deliveryCompanyService;

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryCompanyDTO> getDeliveryCompany(@PathVariable UUID id) {
        return ResponseEntity.ok(deliveryCompanyService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<DeliveryCompanyDTO>> getAllDeliveryCompany() {
        return ResponseEntity.ok(deliveryCompanyService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<DeliveryCompanyDTO> createDeliveryCompany(@RequestBody @Valid DeliveryCompanyDTO deliveryCompanyDTO) {
        return ResponseEntity.ok(deliveryCompanyService.create(deliveryCompanyDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDeliveryCompany(@PathVariable UUID id) {
        deliveryCompanyService.delete(id);
    }

}
