package com.alexandre.inventoryservice.controller;

import com.alexandre.inventoryservice.dto.VendorDTO;
import com.alexandre.inventoryservice.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/api/v1/vendor")
public class VendorController {
    private final VendorService vendorService;

    @GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable UUID id) {
        return ResponseEntity.ok(vendorService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<VendorDTO> createVendor(@RequestBody VendorDTO vendorDTO) {
        return ResponseEntity.ok(vendorService.create(vendorDTO));
    }

    @PutMapping("/")
    public ResponseEntity<VendorDTO> updateVendor(@RequestBody VendorDTO vendorDTO) {
        return ResponseEntity.ok(vendorService.update(vendorDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable UUID id) {
        vendorService.deleteById(id);
    }
}
