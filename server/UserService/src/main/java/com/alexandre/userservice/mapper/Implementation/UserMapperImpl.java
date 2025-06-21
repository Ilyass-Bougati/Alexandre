package com.alexandre.userservice.mapper.Implementation;

import com.alexandre.userservice.dto.UserDTO;
import com.alexandre.userservice.entity.User;
import com.alexandre.userservice.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .address(user.getAddress())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Override
    public User toUser(UserDTO userDTO) {
        return User.builder()
                .address(userDTO.getAddress())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}
