package com.alexandre.inventoryservice.mapper.implementation;

import com.alexandre.inventoryservice.dto.VariationImageDTO;
import com.alexandre.inventoryservice.entity.VariationImage;
import com.alexandre.inventoryservice.mapper.VariationImageMapper;

public class VariationImageMapperImpl implements VariationImageMapper {
    @Override
    public VariationImageDTO toDto(VariationImage variationImage) {
        return VariationImageDTO.builder()
                .uri(variationImage.getUri())
                .variationId(variationImage.getVariation().getId())
                .id(variationImage.getId())
                .thumbnail(variationImage.getThumbnail())
                .orderIndex(variationImage.getOrderIndex())
                .build();
    }

    @Override
    public VariationImage toEntity(VariationImageDTO variationImageDTO) {
        // TODO : Adding the variation
        return VariationImage.builder()
                .uri(variationImageDTO.getUri())
                .id(variationImageDTO.getId())
                .thumbnail(variationImageDTO.getThumbnail())
                .orderIndex(variationImageDTO.getOrderIndex())
                .build();
    }
}
