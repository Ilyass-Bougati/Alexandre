package com.alexandre.inventoryservice.mapper.implementation;

import com.alexandre.inventoryservice.dto.ProductVariationDTO;
import com.alexandre.inventoryservice.entity.ProductVariation;
import com.alexandre.inventoryservice.mapper.ProductVariationMapper;
import com.alexandre.inventoryservice.service.product.ProductEntityService;
import com.alexandre.inventoryservice.service.warehouse.WarehouseEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductVariationMapperImpl implements ProductVariationMapper {

    private final ProductEntityService productEntityService;
    private final WarehouseEntityService warehouseEntityService;

    @Override
    public ProductVariationDTO toDto(ProductVariation productVariation) {
        ProductVariationDTO productVariationDTO = ProductVariationDTO.builder()
                .id(productVariation.getId())
                .sku(productVariation.getSku())
                .availableQuantity(productVariation.getAvailableQuantity())
                .inventoryQuantity(productVariation.getInventoryQuantity())
                .type(productVariation.getType())
                .unitPrice(productVariation.getUnitPrice())
                .defaultVariation(productVariation.getDefaultVariation())
                .build();

        if (productVariation.getProduct() != null) {
            productVariationDTO.setProductId(productVariation.getProduct().getId());
        }

        if (productVariation.getWarehouse() != null) {
            productVariationDTO.setWarehouseId(productVariation.getWarehouse().getId());
        }

        return productVariationDTO;
    }

    @Override
    public ProductVariation toEntity(ProductVariationDTO productVariationDTO) {
        return ProductVariation.builder()
                .id(productVariationDTO.getId())
                .sku(productVariationDTO.getSku())
                .availableQuantity(productVariationDTO.getAvailableQuantity())
                .inventoryQuantity(productVariationDTO.getInventoryQuantity())
                .type(productVariationDTO.getType())
                .unitPrice(productVariationDTO.getUnitPrice())
                .defaultVariation(productVariationDTO.getDefaultVariation())
                .product(productEntityService.findById(productVariationDTO.getProductId()))
                .warehouse(warehouseEntityService.findById(productVariationDTO.getWarehouseId()))
                .build();
    }
}
