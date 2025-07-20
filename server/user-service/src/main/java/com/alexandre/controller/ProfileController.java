package com.alexandre.controller;

import com.alexandre.dto.ProfileDTO;
import com.alexandre.record.UserPrincipal;
import com.alexandre.service.profile.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile/api/v1")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/")
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userPrincipal.profile());
    }

    @PutMapping("/")
    public ResponseEntity<ProfileDTO> updateProfile(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid ProfileDTO profileDTO) {
        // making sure the user is changing their profile
        profileDTO.setId(userPrincipal.profile().getId());
        return ResponseEntity.ok(profileService.update(profileDTO));
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        profileService.deleteById(userPrincipal.profile().getId());
        return ResponseEntity.ok().build();
    }
}
