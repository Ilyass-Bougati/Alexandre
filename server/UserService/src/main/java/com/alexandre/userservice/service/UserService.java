package com.alexandre.userservice.service;

import com.alexandre.userservice.dto.UserDTO;

import java.util.UUID;

public interface UserService extends CrudService<UserDTO, UUID> {
}
