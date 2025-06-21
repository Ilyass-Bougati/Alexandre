package com.alexandre.userservice.service.implementation;

import com.alexandre.userservice.dto.UserDTO;
import com.alexandre.userservice.entity.User;
import com.alexandre.userservice.mapper.UserMapper;
import com.alexandre.userservice.repository.UserRepository;
import com.alexandre.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO getUserById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        // TODO : replace this with custom exception
        return userOptional.map(userMapper::toUserDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        return userMapper.toUserDTO(userRepository.save(user));
    }

    // TODO : I'm sure this could be refactored
    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User oldUserOptional = userRepository.findById(userDTO.getId())
                .orElseThrow(RuntimeException::new);

        oldUserOptional.setFirstName(userDTO.getFirstName());
        oldUserOptional.setLastName(userDTO.getLastName());
        oldUserOptional.setEmail(userDTO.getEmail());
        oldUserOptional.setAddress(userDTO.getAddress());
        userRepository.save(oldUserOptional);
        return userMapper.toUserDTO(oldUserOptional);
    }

    @Override
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }
}
