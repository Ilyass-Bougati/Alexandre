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
public class PaymentMethodControllerTest {

    @LocalServerPort
    int port;
    WebClient webClient;
    static String token;
    static String unprivilegedToken;

    private final String email = "pm.test@gmail.com";
    private final String password = "pm.test.password";
    private final String unprivilegedEmail = "up.pm.user@gmail.com";
    private final String unprivilegedPassword = "up.user.password";
    static PaymentMethodDTO paymentMethodDTO;

    @BeforeEach
    void setUp() {
        // Base URL = http://localhost:{randomPort}
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @Order(1)
    void createPaymentMethod() {
        // registering the new user
        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .password(password)
                .lastName("auth")
                .firstName("test")
                .phoneNumber("6666666666")
                .build();

        // registering the user
        ResponseEntity<Void> registerRes = webClient.post()
                .uri("/auth/api/v1/register/")
                .bodyValue(request)
                .retrieve()
                .toEntity(Void.class)
                .block();

        assertThat(registerRes.getStatusCode()).isEqualTo(HttpStatus.OK);

        // creating the address
        ResponseEntity<AuthenticationResponse> res = AuthUtils.login(email, password);
        Assertions.assertNotNull(res.getBody());
        token = res.getBody().getAccess_token();

        // Creating the payment method
        PaymentMethodDTO paymentMethodDTO = PaymentMethodDTO.builder()
                .cardHolderName("BOUGATI ILYASS")
                .last4Digits("1111")
                .stripeToken("stripetoken")
                .build();

        ResponseEntity<PaymentMethodDTO> createdPaymentMethod = webClient.post()
                .uri("/paymentMethod/api/v1/")
                .bodyValue(paymentMethodDTO)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(PaymentMethodDTO.class)
                .block();

        this.paymentMethodDTO = createdPaymentMethod.getBody();
        assertThat(createdPaymentMethod.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    void getPaymentMethod() {
        ResponseEntity<PaymentMethodDTO> createdPaymentMethod = webClient.get()
                .uri("/paymentMethod/api/v1/" + paymentMethodDTO.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(PaymentMethodDTO.class)
                .block();

        assertThat(createdPaymentMethod.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdPaymentMethod.getBody().getCardHolderName()).isEqualTo(paymentMethodDTO.getCardHolderName());
    }

    @Test
    @Order(3)
    void failGetPaymentMethod() {
        // registering the new user
        RegisterRequest request = RegisterRequest.builder()
                .email(unprivilegedEmail)
                .password(unprivilegedPassword)
                .lastName("auth")
                .firstName("test")
                .phoneNumber("66666666666")
                .build();

        // registering the user
        ResponseEntity<Void> registerRes = webClient.post()
                .uri("/auth/api/v1/register/")
                .bodyValue(request)
                .retrieve()
                .toEntity(Void.class)
                .block();

        assertThat(registerRes.getStatusCode()).isEqualTo(HttpStatus.OK);

        // creating the address
        ResponseEntity<AuthenticationResponse> res = AuthUtils.login(unprivilegedEmail, unprivilegedPassword);
        Assertions.assertNotNull(res.getBody());
        unprivilegedToken = res.getBody().getAccess_token();

        ResponseEntity<String> paymentMethod = webClient.get()
                .uri("/paymentMethod/api/v1/" + paymentMethodDTO.getId())
                .header("Authorization", "Bearer " + unprivilegedToken)
                .exchangeToMono(clientResponse ->
                        clientResponse.toEntity(String.class)
                )
                .block();

        assertThat(paymentMethod.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @Order(4)
    void failUpdatePaymentMethod() {
        paymentMethodDTO.setCardHolderName("BOUGATI ILYASSS");
        ResponseEntity<String> updatedPaymentMethod = webClient.put()
                .uri("/paymentMethod/api/v1/")
                .bodyValue(paymentMethodDTO)
                .header("Authorization", "Bearer " + unprivilegedToken)
                .exchangeToMono(clientResponse ->
                        clientResponse.toEntity(String.class)
                )
                .block();

        assertThat(updatedPaymentMethod.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }


    @Test
    @Order(5)
    void updatePaymentMethod() {
        paymentMethodDTO.setCardHolderName("BOUGATI ILYASSS");
        ResponseEntity<PaymentMethodDTO> updatedPaymentMethod = webClient.put()
                .uri("/paymentMethod/api/v1/")
                .bodyValue(paymentMethodDTO)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(PaymentMethodDTO.class)
                .block();

        assertThat(updatedPaymentMethod.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedPaymentMethod.getBody().getCardHolderName()).isEqualTo(paymentMethodDTO.getCardHolderName());
    }

    @Test
    @Order(6)
    void failDeletePaymentMethod() {
        ResponseEntity<String> updatedPaymentMethod = webClient.delete()
                .uri("/paymentMethod/api/v1/" + paymentMethodDTO.getId())
                .header("Authorization", "Bearer " + unprivilegedToken)
                .exchangeToMono(clientResponse ->
                        clientResponse.toEntity(String.class)
                )
                .block();

        assertThat(updatedPaymentMethod.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @Order(7)
    void deletePaymentMethod() {
        ResponseEntity<PaymentMethodDTO> updatedPaymentMethod = webClient.delete()
                .uri("/paymentMethod/api/v1/" + paymentMethodDTO.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(PaymentMethodDTO.class)
                .block();

        assertThat(updatedPaymentMethod.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
