package com.alexandre.userservice.record;

import com.alexandre.userservice.dto.ProfileDTO;

import java.io.Serializable;

public record UserPrincipal(String userId, ProfileDTO profile) implements Serializable {}

