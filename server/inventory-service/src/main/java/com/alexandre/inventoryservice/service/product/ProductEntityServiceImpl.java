package com.alexandre.inventoryservice.service.product;

import com.alexandre.inventoryservice.entity.Product;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductEntityServiceImpl implements ProductEntityService {

    private final ProductRepository productRepository;

    @Override
    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }
}
