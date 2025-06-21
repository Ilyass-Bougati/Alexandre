package com.alexandre.userservice.repository;

import com.alexandre.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(UUID id);
    void deleteById(UUID id);
}
