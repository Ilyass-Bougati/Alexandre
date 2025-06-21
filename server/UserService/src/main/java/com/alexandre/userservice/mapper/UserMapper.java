package com.alexandre.userservice.mapper;

import com.alexandre.userservice.dto.UserDTO;
import com.alexandre.userservice.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO toUserDTO(User user);
    User toUser(UserDTO userDTO);
}
