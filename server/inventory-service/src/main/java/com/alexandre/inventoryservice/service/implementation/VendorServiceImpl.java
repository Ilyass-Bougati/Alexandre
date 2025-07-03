package com.alexandre.inventoryservice.service.implementation;

import com.alexandre.inventoryservice.dto.VendorDTO;
import com.alexandre.inventoryservice.entity.Vendor;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.mapper.VendorMapper;
import com.alexandre.inventoryservice.repository.VendorRepository;
import com.alexandre.inventoryservice.service.VendorService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    @Override
    @Transactional(readOnly = true)
    public VendorDTO findById(UUID id) {
        return vendorRepository
                .findById(id)
                .map(vendorMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Vendor not found"));
    }

    @Override
    public VendorDTO create(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.toEntity(vendorDTO);
        return vendorMapper.toDto(vendorRepository.save(vendor));
    }

    /**
     * This doesn't change the products
     * @param vendorDTO the new vendor data, the id is required
     * @return the updated vendor data
     */
    @Override
    public VendorDTO update(VendorDTO vendorDTO) {
        Vendor oldVendorOptional = vendorRepository
                .findById(vendorDTO.getId())
                .orElseThrow(() -> new NotFoundException("Vendor not found"));


        oldVendorOptional.setName(vendorDTO.getName());
        oldVendorOptional.setEmail(vendorDTO.getEmail());
        oldVendorOptional.setAddress(vendorDTO.getAddress());
        oldVendorOptional.setPhoneNumber(vendorDTO.getPhoneNumber());

        vendorRepository.save(oldVendorOptional);
        return vendorMapper.toDto(oldVendorOptional);
    }

    @Override
    public void deleteById(UUID uuid) {
        vendorRepository.deleteById(uuid);
    }
}
