package com.alexandre.inventoryservice.mapper.implementation;

import com.alexandre.inventoryservice.dto.ProductDTO;
import com.alexandre.inventoryservice.entity.Product;
import com.alexandre.inventoryservice.mapper.ProductMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDto(Product product) {
        // TODO : Add the product images
        return ProductDTO.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .build();
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        // TODO : Add the product images
        return Product.builder()
                .description(productDTO.getDescription())
                .name(productDTO.getName())
                .build();
    }
}
