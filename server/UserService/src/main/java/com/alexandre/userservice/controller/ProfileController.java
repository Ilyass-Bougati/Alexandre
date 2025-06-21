package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.ProfileDTO;
import com.alexandre.userservice.service.ProfileService;
import com.alexandre.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(profileService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProfileDTO> createProfile(@RequestBody @Valid ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.create(profileDTO));
    }

    @PutMapping
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody @Valid ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.update(profileDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
        profileService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
