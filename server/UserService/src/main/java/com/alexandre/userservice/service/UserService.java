package com.alexandre.userservice.service;

import com.alexandre.userservice.dto.UserDTO;

import java.util.UUID;

public interface UserService {
    UserDTO getUserById(UUID id);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    void deleteUserById(UUID id);
}
