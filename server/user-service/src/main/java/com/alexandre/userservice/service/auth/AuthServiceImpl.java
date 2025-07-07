package com.alexandre.userservice.service.auth;

import com.alexandre.userservice.dto.ProfileDTO;
import com.alexandre.userservice.dto.RegisterRequest;
import com.alexandre.userservice.service.keycloak.KeycloakService;
import com.alexandre.userservice.service.profile.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final ProfileService profileService;
    private final KeycloakService keycloakService;

    @Override
    public void login(String email, String password) {

    }

    @Override
    public void register(@Valid RegisterRequest registerRequest) {
        // creating the ProfileDTO
        ProfileDTO profileDTO = ProfileDTO.builder()
                .phoneNumber(registerRequest.getPhoneNumber())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .build();

        // registering the user
        UUID userId = keycloakService.registerUser(
                registerRequest.getEmail(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getPassword()
        );

        // saving the profile
        profileDTO.setUserId(userId);
        profileService.create(profileDTO);
    }
}
