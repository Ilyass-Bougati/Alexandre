package com.alexandre.inventoryservice.mapper.implementation;

import com.alexandre.inventoryservice.dto.VendorDTO;
import com.alexandre.inventoryservice.entity.Vendor;
import com.alexandre.inventoryservice.mapper.VendorMapper;
import org.springframework.stereotype.Service;

@Service
public class VendorMapperImpl implements VendorMapper {
    @Override
    public VendorDTO toDto(Vendor vendor) {
        return VendorDTO.builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .email(vendor.getEmail())
                .address(vendor.getAddress())
                .phoneNumber(vendor.getPhoneNumber())
                .build();
    }

    @Override
    public Vendor toEntity(VendorDTO vendorDTO) {
        return Vendor.builder()
                .id(vendorDTO.getId())
                .name(vendorDTO.getName())
                .email(vendorDTO.getEmail())
                .address(vendorDTO.getAddress())
                .phoneNumber(vendorDTO.getPhoneNumber())
                .build();
    }
}
