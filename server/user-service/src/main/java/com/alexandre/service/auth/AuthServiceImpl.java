package com.alexandre.service.auth;

import com.alexandre.dto.ProfileDTO;
import com.alexandre.dto.RegisterRequest;
import com.alexandre.event.RegisterEvent;
import com.alexandre.service.keycloak.KeycloakService;
import com.alexandre.service.profile.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final ProfileService profileService;
    private final KeycloakService keycloakService;
    private final KafkaTemplate<String, RegisterEvent> kafkaTemplate;

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
        ProfileDTO profile = profileService.create(profileDTO);

        // producing an event
        RegisterEvent registerEvent = RegisterEvent.builder()
                .profileId(profile.getId())
                .email(profile.getEmail())
                .lastName(profile.getLastName())
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        kafkaTemplate.send("user.register", registerEvent);
    }
}
