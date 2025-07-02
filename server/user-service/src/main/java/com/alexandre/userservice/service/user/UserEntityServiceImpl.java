package com.alexandre.userservice.service.user;

import com.alexandre.userservice.entity.User;
import com.alexandre.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepository userRepository;

    @Override
    public User findById(UUID uuid) {
        return userRepository.findById(uuid)
                .orElse(null);
    }
}
