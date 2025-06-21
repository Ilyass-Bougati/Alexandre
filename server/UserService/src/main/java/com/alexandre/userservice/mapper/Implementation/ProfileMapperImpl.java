package com.alexandre.userservice.mapper.Implementation;

import com.alexandre.userservice.dto.ProfileDTO;
import com.alexandre.userservice.entity.Profile;
import com.alexandre.userservice.mapper.ProfileMapper;
import org.springframework.stereotype.Service;

@Service
public class ProfileMapperImpl implements ProfileMapper {
    @Override
    public ProfileDTO toDto(Profile profile) {
        return ProfileDTO.builder()
                .address(profile.getAddress())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phoneNumber(profile.getPhoneNumber())
                .id(profile.getId())
                .build();
    }

    @Override
    public Profile toEntity(ProfileDTO profileDTO) {
        return Profile.builder()
                .address(profileDTO.getAddress())
                .firstName(profileDTO.getFirstName())
                .lastName(profileDTO.getLastName())
                .phoneNumber(profileDTO.getPhoneNumber())
                .build();
    }
}
