package com.alexandre.service.order;

import com.alexandre.dto.response.OrderDTO;
import com.alexandre.record.StripeProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service("orderService")
public class OrderService {

    private final WebClient webClient;

    public OrderService(WebClient.Builder clientBuilder) {
        this.webClient = clientBuilder.build();
    }

    public OrderDTO get(UUID id, Jwt jwt) {
        ResponseEntity<OrderDTO> orderDTOResponseEntity = null;
        try {
            orderDTOResponseEntity = webClient.get()
                    .uri("http://order-service/order/api/v1/" + id)
                    .header("Authorization", "Bearer " + jwt.getTokenValue())
                    .retrieve()
                    .toEntity(OrderDTO.class)
                    .block();
        } catch (Exception e) {
            // TODO : Refactor this
            throw new RuntimeException(e.getMessage());
        }

        return orderDTOResponseEntity.getBody();
    }

    public Boolean checkProfileOwnsOrder(UUID profileId, UUID orderId, Jwt jwt) {
        OrderDTO orderDTO = get(orderId, jwt);
        return orderDTO.getProfileId().equals(profileId);
    }
}
