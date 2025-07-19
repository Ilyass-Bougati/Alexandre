package com.alexandre.records;

import com.alexandre.dto.ProfileDTO;
import org.springframework.security.oauth2.jwt.Jwt;

import java.io.Serializable;

public record UserPrincipal(Jwt token, String userId, ProfileDTO profile) implements Serializable {}

