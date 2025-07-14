package com.alexandre.orderservice.service.product;

import com.alexandre.orderservice.dto.ProductVariationDTO;

import java.util.UUID;

public interface ProductVariationService {
    ProductVariationDTO get(UUID productVariationId);
}
