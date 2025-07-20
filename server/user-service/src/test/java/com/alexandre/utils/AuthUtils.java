package com.alexandre.utils;

import com.alexandre.dto.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


public class AuthUtils {
    public static MultiValueMap<String, String> registerFormData(String email, String password) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", email);
        formData.add("password", password);
        formData.add("grant_type", "password");
        formData.add("client_id", "public-client");

        return formData;
    }

    public static ResponseEntity<AuthenticationResponse> login(String email, String password) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();

        return webClient.post()
                .uri("/realms/Alexandre/protocol/openid-connect/token")
                .body(BodyInserters.fromFormData(AuthUtils.registerFormData(email, password)))
                .retrieve()
                .toEntity(AuthenticationResponse.class)
                .block();
    }
}
