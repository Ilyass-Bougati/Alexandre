package com.alexandre.orderservice.service.product;

import com.alexandre.orderservice.dto.ProductDTO;
import com.alexandre.orderservice.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final WebClient webClient;

    public ProductServiceImpl(WebClient.Builder clientBuilder) {
        this.webClient = clientBuilder.build();
    }

    public ProductDTO get(UUID productId) {
        ResponseEntity<ProductDTO> res;
        try {
            res = webClient.get()
                    .uri("http://inventory-service/inventory/api/v1/product/" + productId)
                    .retrieve()
                    .toEntity(ProductDTO.class)
                    .block();
        } catch (Exception e) {
            throw new NotFoundException("Product not found");
        }

        return res.getBody();
    }
}
