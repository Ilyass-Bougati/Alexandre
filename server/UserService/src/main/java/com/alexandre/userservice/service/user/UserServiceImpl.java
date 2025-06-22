package com.alexandre.userservice.service.user;

import com.alexandre.userservice.dto.ProfileDTO;
import com.alexandre.userservice.dto.RegisterRequest;
import com.alexandre.userservice.dto.UserDTO;
import com.alexandre.userservice.entity.Profile;
import com.alexandre.userservice.entity.User;
import com.alexandre.userservice.exception.NotFoundException;
import com.alexandre.userservice.dto.mapper.UserMapper;
import com.alexandre.userservice.repository.ProfileRepository;
import com.alexandre.userservice.repository.UserRepository;
import com.alexandre.userservice.service.profile.ProfileEntityService;
import com.alexandre.userservice.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final ProfileService profileService;
    private final ProfileEntityService profileEntityService;
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

    @Override
    public void registerUser(RegisterRequest registerRequest) {
        // Creating the user
        UserDTO userDTO = UserDTO.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();
        userDTO = create(userDTO);

        // Creating the profile
        ProfileDTO profileDTO = ProfileDTO.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .phoneNumber(registerRequest.getLastName())
                .userId(userDTO.getId())
                .build();
        profileDTO = profileService.create(profileDTO);


        // updating the User to add profile_id
        Profile profile = profileEntityService.findById(profileDTO.getId());
        if (profile == null) {
            // can this happen? idk
            throw new NotFoundException("Profile not found");
        }

        User user = userRepository.findById(userDTO.getId())
                // is this possible? idk
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setProfile(profile);
        userRepository.save(user);
    }
}
