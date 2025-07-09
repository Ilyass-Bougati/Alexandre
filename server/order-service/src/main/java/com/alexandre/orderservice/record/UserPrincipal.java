package com.alexandre.orderservice.record;

import com.alexandre.orderservice.dto.ProfileDTO;

import java.io.Serializable;

public record UserPrincipal(String userId, ProfileDTO profile) implements Serializable {}
