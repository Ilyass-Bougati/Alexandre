package com.alexandre.inventoryservice.controller;

import com.alexandre.inventoryservice.dto.WarehouseDTO;
import com.alexandre.inventoryservice.service.warehouse.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('alex_admin', 'alex_staff')")
@RestController
@RequestMapping("/inventory/api/v1/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> getWarehouseById(@PathVariable UUID id) {
        return ResponseEntity.ok(warehouseService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<WarehouseDTO> createWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        return ResponseEntity.ok(warehouseService.create(warehouseDTO));
    }

    @PutMapping("/")
    public ResponseEntity<WarehouseDTO> updateWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        return ResponseEntity.ok(warehouseService.update(warehouseDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWarehouse(@PathVariable UUID id) {
        warehouseService.deleteById(id);
    }
}
