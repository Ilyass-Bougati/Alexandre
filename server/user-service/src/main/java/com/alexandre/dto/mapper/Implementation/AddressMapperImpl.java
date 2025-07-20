package com.alexandre.dto.mapper.Implementation;

import com.alexandre.dto.AddressDTO;
import com.alexandre.entity.Address;
import com.alexandre.dto.mapper.AddressMapper;
import com.alexandre.service.city.CityEntityService;
import com.alexandre.service.profile.ProfileEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressMapperImpl implements AddressMapper {

    private final ProfileEntityService profileEntityService;
    private final CityEntityService cityEntityService;

    @Override
    public AddressDTO toDto(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .isDefault(address.getIsDefault())
                .cityId(address.getCity().getId())
                .profileId(address.getProfile().getId())
                .postalCode(address.getPostalCode())
                .createdAt(address.getCreatedAt())
                .build();
    }

    @Override
    public Address toEntity(AddressDTO addressDTO) {
        return Address.builder()
                .id(addressDTO.getId())
                .postalCode(addressDTO.getPostalCode())
                .street(addressDTO.getStreet())
                .isDefault(addressDTO.getIsDefault())
                .profile(profileEntityService.findById(addressDTO.getProfileId()))
                .city(cityEntityService.findById(addressDTO.getCityId()))
                .createdAt(addressDTO.getCreatedAt())
                .build();
    }
}
