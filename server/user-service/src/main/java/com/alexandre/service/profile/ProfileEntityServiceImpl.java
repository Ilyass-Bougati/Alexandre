package com.alexandre.service.profile;

import com.alexandre.entity.Profile;
import com.alexandre.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class ProfileEntityServiceImpl implements ProfileEntityService {
    private final ProfileRepository profileRepository;

    @Override
    public Profile findById(UUID uuid) {
        return profileRepository.findById(uuid)
                .orElse(null);
    }
}
