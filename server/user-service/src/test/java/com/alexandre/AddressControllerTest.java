package com.alexandre;

import com.alexandre.dto.AddressDTO;
import com.alexandre.dto.AuthenticationResponse;
import com.alexandre.dto.CityDTO;
import com.alexandre.dto.RegisterRequest;
import com.alexandre.utils.AuthUtils;
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
    private final String unprivilegedEmail = "up.address.user@gmail.com";
    private final String unprivilegedPassword = "up.user.password";
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

        address = createdAddress.getBody();
        assertThat(createdAddress.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    public void updateAddress() {
        // updating the address
        address.setStreet("Street 2");

        ResponseEntity<AddressDTO> createdAddress = webClient.post()
                .uri("/address/api/v1/")
                .bodyValue(address)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(AddressDTO.class)
                .block();

        assertThat(createdAddress.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdAddress.getBody().getStreet()).isEqualTo(address.getStreet());
    }

    @Test
    @Order(3)
    public void getAddress() {
        ResponseEntity<AddressDTO> createdAddress = webClient.get()
                .uri("/address/api/v1/" + address.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(AddressDTO.class)
                .block();

        assertThat(createdAddress.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdAddress.getBody().getStreet()).isEqualTo(address.getStreet());
    }

    @Test
    @Order(4)
    public void deleteAddress() {
        ResponseEntity<AddressDTO> createdAddress = webClient.delete()
                .uri("/address/api/v1/" + address.getId())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(AddressDTO.class)
                .block();

        assertThat(createdAddress.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(5)
    public void failUpdateAddress() {
        RegisterRequest request = RegisterRequest.builder()
                .email(unprivilegedEmail)
                .password(unprivilegedPassword)
                .lastName("auth")
                .firstName("test")
                .phoneNumber("555555555")
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
        token = authRes.getBody().getAccess_token();

        // updating the address
        address.setStreet("Street 3");

        ResponseEntity<String> responseEntity = webClient.put()
                .uri("/address/api/v1/")
                .bodyValue(address)
                .header("Authorization", "Bearer " + token)
                .exchangeToMono(clientResponse ->
                        clientResponse.toEntity(String.class)
                )
                .block();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @Order(6)
    public void failDeleteAddress() {
        ResponseEntity<String> responseEntity = webClient.delete()
                .uri("/address/api/v1/" + address.getId())
                .header("Authorization", "Bearer " + token)
                .exchangeToMono(clientResponse ->
                        clientResponse.toEntity(String.class)
                )
                .block();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}
