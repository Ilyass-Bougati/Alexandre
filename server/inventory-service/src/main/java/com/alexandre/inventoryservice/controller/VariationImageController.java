package com.alexandre.inventoryservice.controller;

import com.alexandre.inventoryservice.dto.VariationImageDTO;
import com.alexandre.inventoryservice.service.VariationImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/api/v1/variationImage")
public class VariationImageController {
    private final VariationImageService variationImageService;

    @GetMapping("/{id}")
    public ResponseEntity<VariationImageDTO> getVariationImageById(@PathVariable UUID id) {
        return ResponseEntity.ok(variationImageService.findById(id));
    }

    @PreAuthorize("hasAnyRole('alex_admin', 'alex_staff')")
    @PostMapping("/")
    public ResponseEntity<VariationImageDTO> createVariationImage(@RequestBody VariationImageDTO variationImageDTO) {
        return ResponseEntity.ok(variationImageService.create(variationImageDTO));
    }

    @PreAuthorize("hasAnyRole('alex_admin', 'alex_staff')")
    @PutMapping("/")
    public ResponseEntity<VariationImageDTO> updateVariationImage(@RequestBody VariationImageDTO variationImageDTO) {
        return ResponseEntity.ok(variationImageService.update(variationImageDTO));
    }

    @PreAuthorize("hasAnyRole('alex_admin', 'alex_staff')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVariationImage(@PathVariable UUID id) {
        variationImageService.deleteById(id);
    }
}
