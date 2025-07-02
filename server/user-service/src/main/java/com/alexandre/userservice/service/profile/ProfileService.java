package com.alexandre.userservice.service.profile;

import com.alexandre.userservice.dto.ProfileDTO;
import com.alexandre.userservice.service.CrudService;

import java.util.UUID;

public interface ProfileService extends CrudService<ProfileDTO, UUID> {
}
