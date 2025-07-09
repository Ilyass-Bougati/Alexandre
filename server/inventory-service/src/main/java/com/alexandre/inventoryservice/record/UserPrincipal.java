package com.alexandre.inventoryservice.record;


import com.alexandre.inventoryservice.dto.ProfileDTO;

import java.io.Serializable;

public record UserPrincipal(String userId, ProfileDTO profile) implements Serializable {}
