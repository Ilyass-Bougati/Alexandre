package com.alexandre.userservice.dto.mapper.Implementation;

import com.alexandre.userservice.dto.ProfileDTO;
import com.alexandre.userservice.entity.Profile;
import com.alexandre.userservice.dto.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileMapperImpl implements ProfileMapper {


    @Override
    public ProfileDTO toDto(Profile profile) {
        return ProfileDTO.builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phoneNumber(profile.getPhoneNumber())
                .id(profile.getId())
                .createdAt(profile.getCreatedAt())
                .email(profile.getEmail())
                .userId(profile.getUserId())
                .build();
    }

    @Override
    public Profile toEntity(ProfileDTO profileDTO) {
        return Profile.builder()
                .firstName(profileDTO.getFirstName())
                .lastName(profileDTO.getLastName())
                .phoneNumber(profileDTO.getPhoneNumber())
                .createdAt(profileDTO.getCreatedAt())
                .email(profileDTO.getEmail())
                .userId(profileDTO.getUserId())
                .build();
    }
}
