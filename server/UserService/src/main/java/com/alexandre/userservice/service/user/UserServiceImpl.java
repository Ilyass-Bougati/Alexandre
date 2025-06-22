package com.alexandre.userservice.service.user;

import com.alexandre.userservice.dto.UserDTO;
import com.alexandre.userservice.entity.User;
import com.alexandre.userservice.exception.NotFoundException;
import com.alexandre.userservice.dto.mapper.UserMapper;
import com.alexandre.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User oldUserOptional = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        oldUserOptional.setEmail(userDTO.getEmail());
        userRepository.save(oldUserOptional);
        return userMapper.toDto(oldUserOptional);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
