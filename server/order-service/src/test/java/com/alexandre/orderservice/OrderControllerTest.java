package com.alexandre.orderservice;

import com.alexandre.orderservice.dto.order.OrderDTO;
import com.alexandre.orderservice.dto.orderItem.OrderItemDTO;
import com.alexandre.orderservice.dto.test.AuthenticationResponse;
import com.alexandre.orderservice.dto.test.RegisterRequest;
import com.alexandre.orderservice.entity.OrderItem;
import com.alexandre.orderservice.utils.AuthUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    WebClient.Builder webClientBuilder;

    @LocalServerPort
    int port;
    static String token;
    WebClient lbWebClient;
    WebClient webClient;

    private final String email = "order.test@gmail.com";
    private final String password = "order.test.password";
    private final String adminUsername = "alex";
    private final String adminPassword = "AlexAdmin";
    private final String unprivilegedEmail = "up.order.user@gmail.com";
    private final String unprivilegedPassword = "up.user.password";
    static OrderDTO order;

    @BeforeEach
    void setUp() {
        this.lbWebClient = webClientBuilder
                .build();
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @Order(1)
    void createOrder() {
        // registering the new user
        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .password(password)
                .lastName("auth")
                .firstName("test")
                .phoneNumber("order.test.pn")
                .build();

        // registering the user
        ResponseEntity<Void> registerRes = lbWebClient.post()
                .uri("http://user-service/auth/api/v1/register/")
                .bodyValue(request)
                .retrieve()
                .toEntity(Void.class)
                .block();

        ResponseEntity<AuthenticationResponse> res = AuthUtils.login(email, password);
        Assertions.assertNotNull(res.getBody());
        token = res.getBody().getAccess_token();

        // creating a new order
        OrderDTO newOrder = OrderDTO.builder().build();
        ResponseEntity<OrderDTO> resOrder = webClient.post()
                .uri("/order/api/v1/")
                .header("Authorization", "Bearer " + token)
                .bodyValue(newOrder)
                .retrieve()
                .toEntity(OrderDTO.class)
                .block();

        assertThat(resOrder.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resOrder.getBody()).isNotNull();
        assertThat(resOrder.getBody().getItems().size()).isEqualTo(0);
        order = resOrder.getBody();
    }

    @Test
    @Order(2)
    void addOrderItem() {
        OrderItemDTO item = OrderItemDTO.builder()
                .unitPriceAtOrderTime(12.2)
                .productId(UUID.randomUUID())
                .productName("product name")
                .quantity(1)
                .build();

        ResponseEntity<Void> res = webClient.post()
                .uri("/item/api/v1/" + order.getId())
                .header("Authorization", "Bearer " + token)
                .bodyValue(item)
                .retrieve()
                .toEntity(Void.class)
                .block();

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);

        // creating a new order
        ResponseEntity<OrderDTO> resOrder = webClient.get()
                .uri("/order/api/v1/" + order.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(OrderDTO.class)
                .block();

        assertThat(resOrder.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resOrder.getBody().getItems().get(0)).isNotNull();
        assertThat(resOrder.getBody().getItems().get(0).getProductName()).isEqualTo("product name");
    }

}
