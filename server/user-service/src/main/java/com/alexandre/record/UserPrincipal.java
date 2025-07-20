package com.alexandre.record;

import com.alexandre.dto.ProfileDTO;

import java.io.Serializable;

public record UserPrincipal(String userId, ProfileDTO profile) implements Serializable {}

