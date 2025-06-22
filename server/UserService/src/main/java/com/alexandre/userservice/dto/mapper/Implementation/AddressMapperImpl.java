package com.alexandre.userservice.dto.mapper.Implementation;

import com.alexandre.userservice.dto.AddressDTO;
import com.alexandre.userservice.entity.Address;
import com.alexandre.userservice.dto.mapper.AddressMapper;
import com.alexandre.userservice.service.city.CityEntityService;
import com.alexandre.userservice.service.profile.ProfileEntityService;
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
                .createdAt(address.getCreatedAt())
                .build();
    }

    @Override
    public Address toEntity(AddressDTO addressDTO) {
        return Address.builder()
                .postalCode(addressDTO.getPostalCode())
                .street(addressDTO.getStreet())
                .isDefault(addressDTO.getIsDefault())
                .profile(profileEntityService.findById(addressDTO.getProfileId()))
                .city(cityEntityService.findById(addressDTO.getCityId()))
                .createdAt(addressDTO.getCreatedAt())
                .build();
    }
}
