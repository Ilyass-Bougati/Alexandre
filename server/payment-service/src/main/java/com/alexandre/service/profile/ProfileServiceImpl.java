package com.alexandre.service.profile;

import com.alexandre.dto.response.ProfileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final WebClient webClient;

    public ProfileServiceImpl(WebClient.Builder clientBuilder) {
        this.webClient = clientBuilder.build();
    }

    @Override
    public ProfileDTO get(Jwt jwt) {
        ResponseEntity<ProfileDTO> profileDTO;
        try {
            profileDTO = webClient.get()
                    .uri("http://user-service/profile/api/v1/")
                    .header("Authorization", "Bearer " + jwt.getTokenValue())
                    .retrieve()
                    .toEntity(ProfileDTO.class)
                    .block();
        } catch (Exception e) {
            log.error("Error getting profile : {}", e.getMessage());
            // TODO : change this exception later
            throw new RuntimeException("Error getting profile");
        }

        if (profileDTO == null) {
            log.error("Error getting profile response is null");
            // TODO : change this exception later
            throw new RuntimeException("Error getting profile");
        }

        return profileDTO.getBody();
    }
}
