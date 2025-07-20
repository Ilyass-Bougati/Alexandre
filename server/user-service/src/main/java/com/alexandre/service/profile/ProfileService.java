package com.alexandre.service.profile;

import com.alexandre.dto.ProfileDTO;
import com.alexandre.service.CrudService;

import java.util.UUID;

public interface ProfileService extends CrudService<ProfileDTO, UUID> {
    ProfileDTO findByUserId(UUID uuid);
}
