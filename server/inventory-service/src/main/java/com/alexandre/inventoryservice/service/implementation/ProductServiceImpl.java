package com.alexandre.inventoryservice.service.implementation;

import com.alexandre.inventoryservice.dto.ProductDTO;
import com.alexandre.inventoryservice.entity.Product;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.mapper.ProductMapper;
import com.alexandre.inventoryservice.repository.ProductRepository;
import com.alexandre.inventoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    @Transactional(readOnly = true)
    public ProductDTO findById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        Product oldProductOptional = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        oldProductOptional.setName(productDTO.getName());
        oldProductOptional.setDescription(productDTO.getDescription());
        oldProductOptional.setPrice(productDTO.getPrice());
        oldProductOptional.setImageUrl(productDTO.getImageUrl());

        productRepository.save(oldProductOptional);
        return productMapper.toDto(oldProductOptional);
    }

    @Override
    public void deleteById(UUID uuid) {
        productRepository.deleteById(uuid);
    }
}
