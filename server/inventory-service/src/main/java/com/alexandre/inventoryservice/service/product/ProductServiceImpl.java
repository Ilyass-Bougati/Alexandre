package com.alexandre.inventoryservice.service.product;

import com.alexandre.inventoryservice.dto.ProductDTO;
import com.alexandre.inventoryservice.entity.Product;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.mapper.ProductMapper;
import com.alexandre.inventoryservice.repository.ProductRepository;
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

    /**
     * note that product images, vendors and variations can't be changed through this method
     * @param productDTO the new product data, note that it's mandatory to provide the id
     * @return the updated product data
     */
    @Override
    public ProductDTO update(ProductDTO productDTO) {
        Product oldProductOptional = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        oldProductOptional.setName(productDTO.getName());
        oldProductOptional.setDescription(productDTO.getDescription());

        productRepository.save(oldProductOptional);
        return productMapper.toDto(oldProductOptional);
    }

    @Override
    public void deleteById(UUID uuid) {
        productRepository.deleteById(uuid);
    }
}
