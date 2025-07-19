package com.alexandre.service.profile;

import com.alexandre.dto.ProfileDTO;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ProfileService {
    ProfileDTO get(Jwt jwt);
}
