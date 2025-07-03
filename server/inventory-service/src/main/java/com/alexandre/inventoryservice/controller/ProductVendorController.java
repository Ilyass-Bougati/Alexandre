package com.alexandre.inventoryservice.controller;

import com.alexandre.inventoryservice.dto.ProductVendorDTO;
import com.alexandre.inventoryservice.service.ProductVendorService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/api/v1/productVendor")
public class ProductVendorController {

    private final ProductVendorService productVendorService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductVendorDTO> getProductVendorById(@PathVariable UUID id) {
        return ResponseEntity.ok(productVendorService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProductVendorDTO> createProductVendor(
            @RequestBody ProductVendorDTO productVendorDTO
    ) {
        return ResponseEntity.ok(productVendorService.create(productVendorDTO));
    }

    @PutMapping("/")
    public ResponseEntity<ProductVendorDTO> updateProductVendor(
            @RequestBody ProductVendorDTO productVendorDTO
    ) {
        return ResponseEntity.ok(productVendorService.update(productVendorDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductVendor(@PathVariable UUID id) {
        productVendorService.deleteById(id);
    }
}
