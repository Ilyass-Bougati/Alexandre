package com.alexandre.inventoryservice.mapper.implementation;

import com.alexandre.inventoryservice.dto.ProductVariationDTO;
import com.alexandre.inventoryservice.entity.ProductVariation;
import com.alexandre.inventoryservice.mapper.ProductVariationMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductVariationMapperImpl implements ProductVariationMapper {
    @Override
    public ProductVariationDTO toDto(ProductVariation productVariation) {
        return ProductVariationDTO.builder()
                .id(productVariation.getId())
                .productId(productVariation.getProduct().getId())
                .sku(productVariation.getSku())
                .availableQuantity(productVariation.getAvailableQuantity())
                .inventoryQuantity(productVariation.getInventoryQuantity())
                .type(productVariation.getType())
                .unitPrice(productVariation.getUnitPrice())
                .warehouseId(productVariation.getWarehouse().getId())
                .defaultVariation(productVariation.getDefaultVariation())
                .build();
    }

    @Override
    public ProductVariation toEntity(ProductVariationDTO productVariationDTO) {
        // TODO : add product and warehouse
        return ProductVariation.builder()
                .id(productVariationDTO.getId())
                .sku(productVariationDTO.getSku())
                .availableQuantity(productVariationDTO.getAvailableQuantity())
                .inventoryQuantity(productVariationDTO.getInventoryQuantity())
                .type(productVariationDTO.getType())
                .unitPrice(productVariationDTO.getUnitPrice())
                .defaultVariation(productVariationDTO.getDefaultVariation())
                .build();
    }
}
