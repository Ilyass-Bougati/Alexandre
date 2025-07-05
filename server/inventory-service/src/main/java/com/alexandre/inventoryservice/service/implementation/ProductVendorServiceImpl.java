package com.alexandre.inventoryservice.service.implementation;

import com.alexandre.inventoryservice.dto.ProductVendorDTO;
import com.alexandre.inventoryservice.entity.ProductVendor;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.mapper.ProductVendorMapper;
import com.alexandre.inventoryservice.repository.ProductVendorRepository;
import com.alexandre.inventoryservice.service.ProductVendorService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductVendorServiceImpl implements ProductVendorService {

    private final ProductVendorRepository productVendorRepository;
    private final ProductVendorMapper productVendorMapper;

    @Override
    @Transactional(readOnly = true)
    public ProductVendorDTO findById(UUID id) {
        return productVendorRepository
                .findById(id)
                .map(productVendorMapper::toDto)
                .orElseThrow(() -> new NotFoundException("ProductVendor not found"));
    }

    @Override
    public ProductVendorDTO create(ProductVendorDTO productVendorDTO) {
        ProductVendor productVendor = productVendorMapper.toEntity(productVendorDTO);
        return productVendorMapper.toDto(productVendorRepository.save(productVendor));
    }

    @Override
    public ProductVendorDTO update(ProductVendorDTO productVendorDTO) {
        ProductVendor oldProductVendorOptional = productVendorRepository
                .findById(productVendorDTO.getId())
                .orElseThrow(() -> new NotFoundException("ProductVendor not found"));


        // TODO : rethink this later, can we change the vendor and the product
        oldProductVendorOptional.setDeliveryDelayDays(productVendorDTO.getDeliveryDelayDays());
        oldProductVendorOptional.setPurchasePrice(productVendorDTO.getPurchasePrice());

        productVendorRepository.save(oldProductVendorOptional);
        return productVendorMapper.toDto(oldProductVendorOptional);
    }

    @Override
    public void deleteById(UUID uuid) {
        productVendorRepository.deleteById(uuid);
    }
}
