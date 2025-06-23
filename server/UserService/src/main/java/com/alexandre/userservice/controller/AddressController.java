package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.AddressDTO;
import com.alexandre.userservice.service.address.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address/api/v1")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody @Valid AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.create(addressDTO));
    }

    @PutMapping
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody @Valid AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.update(addressDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
