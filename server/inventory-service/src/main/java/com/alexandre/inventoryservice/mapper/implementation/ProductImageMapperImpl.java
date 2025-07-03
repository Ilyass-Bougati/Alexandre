package com.alexandre.inventoryservice.mapper.implementation;

import com.alexandre.inventoryservice.dto.ProductImageDTO;
import com.alexandre.inventoryservice.entity.ProductImage;
import com.alexandre.inventoryservice.mapper.ProductImageMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductImageMapperImpl implements ProductImageMapper {
    @Override
    public ProductImageDTO toDto(ProductImage productImage) {
        return ProductImageDTO.builder()
                .id(productImage.getId())
                .productId(productImage.getProduct().getId())
                .uri(productImage.getUri())
                .orderIndex(productImage.getOrderIndex())
                .thumbnail(productImage.getThumbnail())
                .build();
    }

    @Override
    public ProductImage toEntity(ProductImageDTO productImageDTO) {
        // TODO : add the product
        return ProductImage.builder()
                .id(productImageDTO.getId())
                .uri(productImageDTO.getUri())
                .orderIndex(productImageDTO.getOrderIndex())
                .thumbnail(productImageDTO.getThumbnail())
                .build();
    }
}
