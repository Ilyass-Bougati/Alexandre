package com.alexandre;

import com.alexandre.dto.AuthenticationResponse;
import com.alexandre.dto.RegisterRequest;
import com.alexandre.utils.AuthUtils;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {

    @LocalServerPort
    int port;
    WebClient webClient;
    WebClient keycloakClient;
    String token;

    private final String email = "auth.test@gmail.com";
    private final String password = "auth.test.password";

    @BeforeEach
    void setUp() {
        // Base URL = http://localhost:{randomPort}
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        keycloakClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }



    @Test
    @Order(1)
    public void registerUser() {
        // creating the request
        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .password(password)
                .lastName("auth")
                .firstName("test")
                .phoneNumber("1111111111")
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

    @Test
    @Order(2)
    public void loginUser() {
        ResponseEntity<AuthenticationResponse> res = keycloakClient.post()
                .uri("/realms/Alexandre/protocol/openid-connect/token")
                .body(BodyInserters.fromFormData(AuthUtils.registerFormData(email, password)))
                .retrieve()
                .toEntity(AuthenticationResponse.class)
                .block();

        // getting the token
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        token = res.getBody().getAccess_token();

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
