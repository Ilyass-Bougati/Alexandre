package com.alexandre.userservice;

import com.alexandre.userservice.dto.AuthenticationResponse;
import com.alexandre.userservice.dto.CityDTO;
import com.alexandre.userservice.dto.RegisterRequest;
import com.alexandre.userservice.utils.AuthUtils;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerTest {

    @LocalServerPort
    int port;
    WebClient webClient;
    static String token;

    private final String email = "alex";
    private final String unprivilegedEmail = "unprivileged.user@gmail.com";
    private final String password = "AlexAdmin";
    private final String unprivilegedPassword = "unprivileged.user.password";
    static CityDTO city;

    @BeforeEach
    void setUp() {
        // Base URL = http://localhost:{randomPort}
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @Order(1)
    public void createCity() {
        ResponseEntity<AuthenticationResponse> res = AuthUtils.login(email, password);

        Assertions.assertNotNull(res.getBody());
        token = res.getBody().getAccess_token();

        // creating the city DTO
        CityDTO cityDTO = CityDTO.builder()
                .name("Marrakech")
                .shippingFee(24.1)
                .build();

        // creating the city
        ResponseEntity<CityDTO> createdCity = webClient.post()
                .uri("/city/api/v1/")
                .bodyValue(cityDTO)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(CityDTO.class)
                .block();

        city = createdCity.getBody();
        assertThat(createdCity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    public void updateCity() {
        // modifying the city DTO
        city.setName("Casa");

        // updating the city
        ResponseEntity<CityDTO> updatedCity = webClient.put()
                .uri("/city/api/v1/")
                .bodyValue(city)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(CityDTO.class)
                .block();

        Assertions.assertNotNull(updatedCity);
        assertThat(updatedCity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(updatedCity.getBody());
        assertThat(updatedCity.getBody().getName()).isEqualTo(city.getName());

        city = updatedCity.getBody();
    }

    @Test
    @Order(3)
    public void getCity() {
        ResponseEntity<CityDTO> updatedCity = webClient.get()
                .uri("/city/api/v1/" + city.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(CityDTO.class)
                .block();

        Assertions.assertNotNull(updatedCity);
        assertThat(updatedCity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(updatedCity.getBody());
        assertThat(updatedCity.getBody().getName()).isEqualTo(city.getName());

        city = updatedCity.getBody();
    }

    @Test
    @Order(4)
    public void deleteCity() {
        ResponseEntity<CityDTO> createdCity = webClient.delete()
                .uri("/city/api/v1/" + city.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(CityDTO.class)
                .block();

        assertThat(createdCity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(5)
    public void failCreateCity() {
        // Creating an unprivileged user
        RegisterRequest request = RegisterRequest.builder()
                .email(unprivilegedEmail)
                .password(unprivilegedPassword)
                .lastName("auth")
                .firstName("test")
                .phoneNumber("0000000000")
                .build();

        // registering the user
        ResponseEntity<Void> res = webClient.post()
                .uri("/auth/api/v1/register/")
                .bodyValue(request)
                .retrieve()
                .toEntity(Void.class)
                .block();

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<AuthenticationResponse> authRes = AuthUtils.login(unprivilegedEmail, unprivilegedPassword);

        Assertions.assertNotNull(authRes.getBody());
        token = authRes.getBody().getAccess_token();

        // creating the city DTO
        CityDTO cityDTO = CityDTO.builder()
                .name("Casa")
                .shippingFee(24.1)
                .build();

        // creating the city
        webClient.post()
                .uri("/city/api/v1/")
                .bodyValue(cityDTO)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        responseEntity -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED)
                );

    }

    @Test
    @Order(6)
    public void failedUpdateCity() {
        // modifying the city DTO
        city.setName("Casa");

        // creating the city
        webClient.put()
                .uri("/city/api/v1/")
                .bodyValue(city)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        responseEntity -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED)
                );
    }

    @Test
    @Order(7)
    public void failDeleteCity() {
        webClient.delete()
                .uri("/city/api/v1/" + city.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        responseEntity -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED)
                );
    }
}
