package com.alexandre.userservice.service.profile;

import com.alexandre.userservice.dto.ProfileDTO;
import com.alexandre.userservice.entity.Profile;
import com.alexandre.userservice.exception.NotFoundException;
import com.alexandre.userservice.dto.mapper.ProfileMapper;
import com.alexandre.userservice.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    @Transactional(readOnly = true)
    public ProfileDTO findById(UUID id) {
        return profileRepository.findById(id)
                .map(profileMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Profile not found"));
    }

    @Override
    public ProfileDTO create(ProfileDTO profileDTO) {
        Profile profile = profileMapper.toEntity(profileDTO);
        return profileMapper.toDto(profileRepository.save(profile));
    }

    @Override
    public ProfileDTO update(ProfileDTO profileDTO) {
        Profile oldProfileOptional = profileRepository.findById(profileDTO.getId())
                .orElseThrow(() -> new NotFoundException("Profile not found"));

        oldProfileOptional.setFirstName(profileDTO.getFirstName());
        oldProfileOptional.setLastName(profileDTO.getLastName());
        oldProfileOptional.setPhoneNumber(profileDTO.getPhoneNumber());

        profileRepository.save(oldProfileOptional);
        return profileMapper.toDto(oldProfileOptional);
    }

    @Override
    public void deleteById(UUID id) {
        profileRepository.deleteById(id);
    }

    @Override
    public ProfileDTO findByUserId(UUID userId) {
        return profileRepository.findByUserId(userId)
                .map(profileMapper::toDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
