package com.alexandre.service.profile;

import com.alexandre.dto.response.ProfileDTO;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public interface ProfileService {
    ProfileDTO get(Jwt jwt);
}
