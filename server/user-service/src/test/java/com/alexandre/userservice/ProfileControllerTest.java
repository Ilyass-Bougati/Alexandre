package com.alexandre.userservice;

import com.alexandre.userservice.dto.*;
import com.alexandre.userservice.utils.AuthUtils;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileControllerTest {

    @LocalServerPort
    int port;
    WebClient webClient;
    static String token;

    private final String email = "profile.test@gmail.com";
    private final String password = "profile.test.password";
    static ProfileDTO profile;

    @BeforeEach
    void setUp() {
        // Base URL = http://localhost:{randomPort}
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @Order(1)
    void getProfile() {
        // registering a new user
        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .password(password)
                .lastName("auth")
                .firstName("test")
                .phoneNumber("3333333333")
                .build();

        // registering the user
        ResponseEntity<Void> registerRes = webClient.post()
                .uri("/auth/api/v1/register/")
                .bodyValue(request)
                .retrieve()
                .toEntity(Void.class)
                .block();

        assertThat(registerRes.getStatusCode()).isEqualTo(HttpStatus.OK);

        // login
        ResponseEntity<AuthenticationResponse> res = AuthUtils.login(email, password);
        Assertions.assertNotNull(res.getBody());
        token = res.getBody().getAccess_token();

        // getting the user's profile
        ResponseEntity<ProfileDTO> getRes = webClient.get()
                .uri("/profile/api/v1/")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(ProfileDTO.class)
                .block();

        profile = getRes.getBody();
        assertThat(getRes.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getRes.getBody().getEmail()).isEqualTo(email);
    }

    @Test
    @Order(2)
    void updateProfile() {
        profile.setFirstName("newFirstName");

        ResponseEntity<ProfileDTO> updateRes = webClient.put()
                .uri("/profile/api/v1/")
                .header("Authorization", "Bearer " + token)
                .bodyValue(profile)
                .retrieve()
                .toEntity(ProfileDTO.class)
                .block();

        assertThat(updateRes.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateRes.getBody().getFirstName()).isEqualTo(profile.getFirstName());
    }

    @Test
    @Order(3)
    void deleteProfile() {
        ResponseEntity<Void> updateRes = webClient.delete()
                .uri("/profile/api/v1/")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(Void.class)
                .block();

        assertThat(updateRes.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
