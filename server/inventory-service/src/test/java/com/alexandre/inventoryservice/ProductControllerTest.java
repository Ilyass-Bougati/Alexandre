package com.alexandre.inventoryservice;

import com.alexandre.inventoryservice.dto.ProductDTO;
import com.alexandre.inventoryservice.dto.test.AuthenticationResponse;
import com.alexandre.inventoryservice.dto.test.RegisterRequest;
import com.alexandre.inventoryservice.utils.AuthUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @Autowired
    WebClient.Builder webClientBuilder;

    @LocalServerPort
    int port;
    static String token;
    static String unprivilegedToken;
    static WebClient lbWebClient;
    static WebClient webClient;

    private static final String adminUsername = "alex";
    private static final String adminPassword = "AlexAdmin";
    private static final String unprivilegedEmail = "up.product.user@gmail.com";
    private static final String unprivilegedPassword = "up.product.password";
    static ProductDTO product;

    @BeforeEach
    void setUp() {
        lbWebClient = webClientBuilder
                .build();
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @Order(1)
    void createProduct() {
        // authenticate the users
        ResponseEntity<AuthenticationResponse> res = AuthUtils.login(adminUsername, adminPassword);
        Assertions.assertNotNull(res.getBody());
        token = res.getBody().getAccess_token();

        // creating the product
        ProductDTO productDTO = ProductDTO.builder()
                .name("product 2")
                .description("product 2 descritpion")
                .build();

        ResponseEntity<ProductDTO> createdProduct = webClient.post()
                .uri("/inventory/api/v1/product/")
                .header("Authorization", "Bearer " + token)
                .bodyValue(productDTO)
                .retrieve()
                .toEntity(ProductDTO.class)
                .block();

        assertThat(createdProduct.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdProduct.getBody().getName()).isEqualTo(productDTO.getName());
        product = createdProduct.getBody();


        ResponseEntity<ProductDTO> fetched = webClient.get()
                .uri("/inventory/api/v1/product/" + product.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(ProductDTO.class)
                .block();

        assertThat(fetched.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fetched.getBody().getName()).isEqualTo(productDTO.getName());
    }

    @Test
    @Order(2)
    void failCreateProduct() {
        // registering the unprivileged user
        // registering the new user
        RegisterRequest request = RegisterRequest.builder()
                .email(unprivilegedEmail)
                .password(unprivilegedPassword)
                .lastName("auth")
                .firstName("test")
                .phoneNumber("product.test.pn")
                .build();

        // registering the user
        ResponseEntity<Void> registerRes = lbWebClient.post()
                .uri("http://user-service/auth/api/v1/register/")
                .bodyValue(request)
                .retrieve()
                .toEntity(Void.class)
                .block();

        ResponseEntity<AuthenticationResponse> res = AuthUtils.login(unprivilegedEmail, unprivilegedPassword);
        Assertions.assertNotNull(res.getBody());
        unprivilegedToken = res.getBody().getAccess_token();

        // creating the product
        ProductDTO productDTO = ProductDTO.builder()
                .name("product 1")
                .description("product 1 descritpion")
                .build();

        ResponseEntity<String> createdProduct = webClient.post()
                .uri("/inventory/api/v1/product/")
                .header("Authorization", "Bearer " + unprivilegedToken)
                .bodyValue(productDTO)
                .exchangeToMono(clientResponse ->
                        clientResponse.toEntity(String.class)
                )
                .block();

        assertThat(createdProduct.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @Order(3)
    void deleteProduct() {
        ResponseEntity<String> res = webClient.delete()
                .uri("/inventory/api/v1/product/" + product.getId())
                .header("Authorization", "Bearer " + unprivilegedToken)
                .exchangeToMono(clientResponse ->
                        clientResponse.toEntity(String.class)
                )
                .block();

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        res = webClient.delete()
                .uri("/inventory/api/v1/product/" + product.getId())
                .header("Authorization", "Bearer " + token)
                .exchangeToMono(clientResponse ->
                        clientResponse.toEntity(String.class)
                )
                .block();

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
