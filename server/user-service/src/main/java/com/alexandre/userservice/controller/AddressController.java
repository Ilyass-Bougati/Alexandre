package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.AddressDTO;
import com.alexandre.userservice.dto.ProfileDTO;
import com.alexandre.userservice.record.UserPrincipal;
import com.alexandre.userservice.service.address.AddressService;
import com.alexandre.userservice.service.auth.AuthService;
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
    public ResponseEntity<AddressDTO> getAddress(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        if (addressService.profileOwnsAddress(userPrincipal.profile().getId(), id)) {
            return ResponseEntity.ok(addressService.findById(id));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(addressService.findByProfileId(userPrincipal.profile().getId()));
    }

    @PostMapping("/")
    public ResponseEntity<AddressDTO> createAddress(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid AddressDTO addressDTO) {
        addressDTO.setProfileId(userPrincipal.profile().getId());
        return ResponseEntity.ok(addressService.create(addressDTO));
    }

    @PutMapping("/")
    public ResponseEntity<AddressDTO> updateAddress(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid AddressDTO addressDTO) {
        if (addressService.profileOwnsAddress(userPrincipal.profile().getId(), addressDTO.getId())) {
            addressDTO.setProfileId(userPrincipal.profile().getId());
            return ResponseEntity.ok(addressService.update(addressDTO));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        if (addressService.profileOwnsAddress(userPrincipal.profile().getId(), id)) {
            addressService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
