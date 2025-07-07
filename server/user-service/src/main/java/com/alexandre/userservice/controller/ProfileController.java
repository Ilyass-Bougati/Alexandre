package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.ProfileDTO;
import com.alexandre.userservice.service.profile.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile/api/v1")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/")
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(profileService.findById(UUID.fromString(jwt.getSubject())));
    }

    @PutMapping("/")
    public ResponseEntity<ProfileDTO> updateProfile(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid ProfileDTO profileDTO) {
        // making sure the user is changing their profile
        profileDTO.setId(UUID.fromString(jwt.getSubject()));
        return ResponseEntity.ok(profileService.update(profileDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@AuthenticationPrincipal Jwt jwt) {
        profileService.deleteById(UUID.fromString(jwt.getSubject()));
        return ResponseEntity.ok().build();
    }
}
