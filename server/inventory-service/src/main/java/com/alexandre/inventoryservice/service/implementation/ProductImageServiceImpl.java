package com.alexandre.inventoryservice.service.implementation;

import com.alexandre.inventoryservice.dto.ProductImageDTO;
import com.alexandre.inventoryservice.entity.ProductImage;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.mapper.ProductImageMapper;
import com.alexandre.inventoryservice.repository.ProductImageRepository;
import com.alexandre.inventoryservice.service.ProductImageService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;

    @Override
    @Transactional(readOnly = true)
    public ProductImageDTO findById(UUID id) {
        return productImageRepository
                .findById(id)
                .map(productImageMapper::toDto)
                .orElseThrow(() -> new NotFoundException("ProductImage not found"));
    }

    @Override
    public ProductImageDTO create(ProductImageDTO productImageDTO) {
        ProductImage productImage = productImageMapper.toEntity(productImageDTO);
        return productImageMapper.toDto(productImageRepository.save(productImage));
    }

    @Override
    public ProductImageDTO update(ProductImageDTO productImageDTO) {
        ProductImage oldProductImageOptional = productImageRepository
                .findById(productImageDTO.getId())
                .orElseThrow(() -> new NotFoundException("ProductImage not found"));


        // TODO : update fields

        productImageRepository.save(oldProductImageOptional);
        return productImageMapper.toDto(oldProductImageOptional);
    }

    @Override
    public void deleteById(UUID uuid) {
        productImageRepository.deleteById(uuid);
    }
}
