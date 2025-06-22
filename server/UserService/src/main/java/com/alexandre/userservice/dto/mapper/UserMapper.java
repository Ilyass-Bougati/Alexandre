package com.alexandre.userservice.dto.mapper;

import com.alexandre.userservice.dto.UserDTO;
import com.alexandre.userservice.entity.User;
import org.mapstruct.Mapper;

public interface UserMapper extends MapperInterface<User, UserDTO> {
}
