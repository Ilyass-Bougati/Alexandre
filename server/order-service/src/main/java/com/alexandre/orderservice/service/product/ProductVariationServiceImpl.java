package com.alexandre.orderservice.service.product;

import com.alexandre.orderservice.dto.ProductVariationDTO;
import com.alexandre.orderservice.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class ProductVariationServiceImpl implements ProductVariationService {

    private final WebClient webClient;

    public ProductVariationServiceImpl(WebClient.Builder clientBuilder) {
        this.webClient = clientBuilder.build();
    }

    @Override
    public ProductVariationDTO get(UUID productVariationId) {
        ResponseEntity<ProductVariationDTO> res;
        try {
            res = webClient.get()
                    .uri("http://inventory-service/inventory/api/v1/productVariation/" + productVariationId)
                    .retrieve()
                    .toEntity(ProductVariationDTO.class)
                    .block();
        } catch (Exception e) {
            throw new NotFoundException("Product Variation not found");
        }

        return res.getBody();
    }
}
