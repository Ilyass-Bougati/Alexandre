package com.alexandre.inventoryservice.mapper.implementation;

import com.alexandre.inventoryservice.dto.ProductDTO;
import com.alexandre.inventoryservice.entity.Product;
import com.alexandre.inventoryservice.mapper.ProductMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .price(product.getPrice())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .build();
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .price(productDTO.getPrice())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .imageUrl(productDTO.getImageUrl())
                .build();
    }
}
