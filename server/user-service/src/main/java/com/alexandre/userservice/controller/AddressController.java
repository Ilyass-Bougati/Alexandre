package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.AddressDTO;
import com.alexandre.userservice.service.address.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address/api/v1")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddress(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID id) {
        if (addressService.profileOwnsAddress(UUID.fromString(jwt.getSubject()), id)) {
            return ResponseEntity.ok(addressService.findById(id));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(addressService.findByProfileId(UUID.fromString(jwt.getSubject())));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid AddressDTO addressDTO) {
        addressDTO.setProfileId(UUID.fromString(jwt.getSubject()));
        return ResponseEntity.ok(addressService.create(addressDTO));
    }

    @PutMapping
    public ResponseEntity<AddressDTO> updateAddress(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid AddressDTO addressDTO) {
        if (addressService.profileOwnsAddress(UUID.fromString(jwt.getSubject()), addressDTO.getProfileId())) {
            return ResponseEntity.ok(addressService.update(addressDTO));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID id) {
        if (addressService.profileOwnsAddress(UUID.fromString(jwt.getSubject()), id)) {
            addressService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
