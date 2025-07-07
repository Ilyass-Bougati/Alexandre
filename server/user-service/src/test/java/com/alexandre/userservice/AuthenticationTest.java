package com.alexandre.userservice;

import com.alexandre.userservice.dto.RegisterRequest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {

    @LocalServerPort
    int port;
    WebClient webClient;

    private final String email = "i.bougati12@gmail.com";
    private final String password = "password";

    @BeforeEach
    void setUp() {
        // Base URL = http://localhost:{randomPort}
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @Order(1)
    public void registerUser() {
        // creating the request
        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .password(password)
                .lastName("Bougati")
                .firstName("Ilyass")
                .phoneNumber("000000")
                .build();

        // registering the user
        ResponseEntity<Void> res = webClient.post()
                .uri("/auth/api/v1/register/")
                .bodyValue(request)
                .retrieve()
                .toEntity(Void.class)
                .block();

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
