package com.alexandre.userservice.dto.mapper.Implementation;

import com.alexandre.userservice.dto.UserDTO;
import com.alexandre.userservice.entity.User;
import com.alexandre.userservice.dto.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}
