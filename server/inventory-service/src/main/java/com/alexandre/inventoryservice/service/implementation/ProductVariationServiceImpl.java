package com.alexandre.inventoryservice.service.implementation;

import com.alexandre.inventoryservice.dto.ProductVariationDTO;
import com.alexandre.inventoryservice.entity.ProductVariation;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.mapper.ProductVariationMapper;
import com.alexandre.inventoryservice.repository.ProductVariationRepository;
import com.alexandre.inventoryservice.service.ProductVariationService;
import java.util.UUID;

import com.alexandre.inventoryservice.service.warehouse.WarehouseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductVariationServiceImpl implements ProductVariationService {

    private final ProductVariationRepository productVariationRepository;
    private final ProductVariationMapper productVariationMapper;
    private final WarehouseServiceImpl warehouseService;

    @Override
    @Transactional(readOnly = true)
    public ProductVariationDTO findById(UUID id) {
        return productVariationRepository
                .findById(id)
                .map(productVariationMapper::toDto)
                .orElseThrow(() -> new NotFoundException("ProductVariation not found"));
    }

    @Override
    public ProductVariationDTO create(ProductVariationDTO productVariationDTO) {
        ProductVariation productVariation = productVariationMapper.toEntity(productVariationDTO);
        return productVariationMapper.toDto(productVariationRepository.save(productVariation));
    }

    @Override
    public ProductVariationDTO update(ProductVariationDTO productVariationDTO) {
        ProductVariation oldProductVariationOptional = productVariationRepository
                .findById(productVariationDTO.getId())
                .orElseThrow(() -> new NotFoundException("ProductVariation not found"));


        // TODO : being able to change the warehouse
        oldProductVariationOptional.setDefaultVariation(productVariationDTO.getDefaultVariation());
        oldProductVariationOptional.setAvailableQuantity(productVariationDTO.getAvailableQuantity());
        oldProductVariationOptional.setInventoryQuantity(productVariationDTO.getInventoryQuantity());
        oldProductVariationOptional.setSku(productVariationDTO.getSku());
        oldProductVariationOptional.setType(productVariationDTO.getType());
        oldProductVariationOptional.setUnitPrice(productVariationDTO.getUnitPrice());

        productVariationRepository.save(oldProductVariationOptional);
        return productVariationMapper.toDto(oldProductVariationOptional);
    }

    @Override
    public void deleteById(UUID uuid) {
        productVariationRepository.deleteById(uuid);
    }
}
