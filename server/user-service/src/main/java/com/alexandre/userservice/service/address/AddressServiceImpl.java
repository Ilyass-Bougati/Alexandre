package com.alexandre.userservice.service.address;

import com.alexandre.userservice.dto.AddressDTO;
import com.alexandre.userservice.dto.mapper.AddressMapper;
import com.alexandre.userservice.entity.Address;
import com.alexandre.userservice.exception.NotFoundException;
import com.alexandre.userservice.repository.AddressRepository;
import com.alexandre.userservice.service.city.CityEntityService;
import com.alexandre.userservice.service.profile.ProfileEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ProfileEntityService profileEntityService;
    private final CityEntityService cityEntityService;

    @Override
    @Transactional(readOnly = true)
    public AddressDTO findById(UUID id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        return addressOptional.map(addressMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Address not found"));
    }

    @Override
    public AddressDTO create(AddressDTO addressDTO) {
        Address address = addressMapper.toEntity(addressDTO);
        return addressMapper.toDto(addressRepository.save(address));
    }

    @Override
    public AddressDTO update(AddressDTO addressDTO) {
        Address oldAddressOptional = addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new NotFoundException("Address not found"));

        oldAddressOptional.setStreet(addressDTO.getStreet());
        oldAddressOptional.setIsDefault(addressDTO.getIsDefault());
        oldAddressOptional.setPostalCode(addressDTO.getPostalCode());
        oldAddressOptional.setProfile(profileEntityService.findById(addressDTO.getProfileId()));
        oldAddressOptional.setCity(cityEntityService.findById(addressDTO.getCityId()));

        addressRepository.save(oldAddressOptional);
        return addressMapper.toDto(oldAddressOptional);
    }

    @Override
    public void deleteById(UUID id) {
        addressRepository.deleteById(id);
    }
}
