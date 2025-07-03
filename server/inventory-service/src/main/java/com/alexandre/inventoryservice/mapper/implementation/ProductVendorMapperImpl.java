package com.alexandre.inventoryservice.mapper.implementation;

import com.alexandre.inventoryservice.dto.ProductVendorDTO;
import com.alexandre.inventoryservice.entity.ProductVendor;
import com.alexandre.inventoryservice.mapper.ProductVendorMapper;

public class ProductVendorMapperImpl implements ProductVendorMapper {
    @Override
    public ProductVendorDTO toDto(ProductVendor productVendor) {
        return ProductVendorDTO.builder()
                .id(productVendor.getId())
                .productId(productVendor.getProduct().getId())
                .vendorId(productVendor.getVendor().getId())
                .purchasePrice(productVendor.getPurchasePrice())
                .deliveryDelayDays(productVendor.getDeliveryDelayDays())
                .build();
    }

    @Override
    public ProductVendor toEntity(ProductVendorDTO productVendorDTO) {
        // TODO : adding the product and the vendor
        return ProductVendor.builder()
                .id(productVendorDTO.getId())
                .purchasePrice(productVendorDTO.getPurchasePrice())
                .deliveryDelayDays(productVendorDTO.getDeliveryDelayDays())
                .build();
    }
}
