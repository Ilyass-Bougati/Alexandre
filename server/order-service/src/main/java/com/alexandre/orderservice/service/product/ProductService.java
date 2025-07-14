package com.alexandre.orderservice.service.product;

import com.alexandre.orderservice.dto.ProductDTO;

import java.util.UUID;

public interface ProductService {
    ProductDTO get(UUID productId);
}
