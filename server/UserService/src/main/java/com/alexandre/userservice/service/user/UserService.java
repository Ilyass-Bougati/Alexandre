package com.alexandre.userservice.service.user;

import com.alexandre.userservice.dto.UserDTO;
import com.alexandre.userservice.service.CrudService;

import java.util.UUID;

public interface UserService extends CrudService<UserDTO, UUID> {
}
