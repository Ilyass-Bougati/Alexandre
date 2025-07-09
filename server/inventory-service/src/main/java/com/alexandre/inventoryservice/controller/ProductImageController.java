package com.alexandre.inventoryservice.controller;

import com.alexandre.inventoryservice.dto.ProductImageDTO;
import com.alexandre.inventoryservice.service.ProductImageService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/api/v1/productImage")
public class ProductImageController {

    private final ProductImageService productImageService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductImageDTO> getProductImageById(@PathVariable UUID id) {
        return ResponseEntity.ok(productImageService.findById(id));
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('alex_admin', 'alex_staff')")
    public ResponseEntity<ProductImageDTO> createProductImage(@RequestBody ProductImageDTO productImageDTO) {
        return ResponseEntity.ok(productImageService.create(productImageDTO));
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyRole('alex_admin', 'alex_staff')")
    public ResponseEntity<ProductImageDTO> updateProductImage(@RequestBody ProductImageDTO productImageDTO) {
        return ResponseEntity.ok(productImageService.update(productImageDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('alex_admin', 'alex_staff')")
    public void deleteProductImage(@PathVariable UUID id) {
        productImageService.deleteById(id);
    }
}
