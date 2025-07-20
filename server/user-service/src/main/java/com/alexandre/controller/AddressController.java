package com.alexandre.controller;

import com.alexandre.dto.AddressDTO;
import com.alexandre.record.UserPrincipal;
import com.alexandre.service.address.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address/api/v1")
public class AddressController {
    private final AddressService addressService;

    @PreAuthorize("@addressService.profileOwnsAddress(#userPrincipal.profile.id, #id)")
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddress(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        return ResponseEntity.ok(addressService.findById(id));
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

    @PreAuthorize("@addressService.profileOwnsAddress(#userPrincipal.profile.id, #addressDTO.id)")
    @PutMapping("/")
    public ResponseEntity<AddressDTO> updateAddress(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid AddressDTO addressDTO) {
        addressDTO.setProfileId(userPrincipal.profile().getId());
        return ResponseEntity.ok(addressService.update(addressDTO));
    }

    @PreAuthorize("@addressService.profileOwnsAddress(#userPrincipal.profile.id, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable UUID id) {
        addressService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
