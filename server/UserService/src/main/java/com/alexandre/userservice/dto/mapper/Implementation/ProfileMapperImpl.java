package com.alexandre.userservice.dto.mapper.Implementation;

import com.alexandre.userservice.dto.ProfileDTO;
import com.alexandre.userservice.entity.Profile;
import com.alexandre.userservice.dto.mapper.ProfileMapper;
import com.alexandre.userservice.service.user.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileMapperImpl implements ProfileMapper {

    private final UserEntityService userEntityService;

    @Override
    public ProfileDTO toDto(Profile profile) {
        return ProfileDTO.builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phoneNumber(profile.getPhoneNumber())
                .userId(profile.getUser().getId())
                .id(profile.getId())
                .build();
    }

    @Override
    public Profile toEntity(ProfileDTO profileDTO) {
        return Profile.builder()
                .firstName(profileDTO.getFirstName())
                .lastName(profileDTO.getLastName())
                .phoneNumber(profileDTO.getPhoneNumber())
                .user(userEntityService.findById(profileDTO.getUserId()))
                .build();
    }
}
