package com.alexandre.userservice;

import com.alexandre.userservice.dto.AddressDTO;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressControllerTest {

    @LocalServerPort
    int port;
    WebClient webClient;
    static String token;

    private final String email = "address.test@gmail.com";
    private final String password = "address.test.password";
    private final String adminUsername = "alex";
    private final String adminPassword = "AlexAdmin";
    static AddressDTO address;
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
    public void createAddress() {
        ResponseEntity<AuthenticationResponse> res = AuthUtils.login(adminUsername, adminPassword);
        Assertions.assertNotNull(res.getBody());
        token = res.getBody().getAccess_token();

        // creating a city
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

        // registering the new user
        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .password(password)
                .lastName("auth")
                .firstName("test")
                .phoneNumber("2222222222")
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
        res = AuthUtils.login(email, password);
        Assertions.assertNotNull(res.getBody());
        token = res.getBody().getAccess_token();

        AddressDTO addressDTO = AddressDTO.builder()
                .cityId(city.getId())
                .street("Street 1")
                .isDefault(true)
                .postalCode("2222")
                .build();

        ResponseEntity<AddressDTO> createdAddress = webClient.post()
                .uri("/address/api/v1/")
                .bodyValue(addressDTO)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(AddressDTO.class)
                .block();

        assertThat(createdAddress.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
