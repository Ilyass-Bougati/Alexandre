package com.alexandre.inventoryservice.controller;

import com.alexandre.inventoryservice.dto.ProductVariationDTO;
import com.alexandre.inventoryservice.service.ProductVariationService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/api/v1/productVariation")
public class ProductVariationController {

    private final ProductVariationService productVariationService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductVariationDTO> getProductVariationById(@PathVariable UUID id) {
        return ResponseEntity.ok(productVariationService.findById(id));
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('alex_admin', 'alex_staff')")
    public ResponseEntity<ProductVariationDTO> createProductVariation(@RequestBody ProductVariationDTO productVariationDTO) {
        return ResponseEntity.ok(productVariationService.create(productVariationDTO));
    }

    @PreAuthorize("hasAnyRole('alex_admin', 'alex_staff')")
    @PutMapping("/")
    public ResponseEntity<ProductVariationDTO> updateProductVariation(@RequestBody ProductVariationDTO productVariationDTO) {
        return ResponseEntity.ok(productVariationService.update(productVariationDTO));
    }

    @PreAuthorize("hasAnyRole('alex_admin', 'alex_staff')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductVariation(@PathVariable UUID id) {
        productVariationService.deleteById(id);
    }
}
