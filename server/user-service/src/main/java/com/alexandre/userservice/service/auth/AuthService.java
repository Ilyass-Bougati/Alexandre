package com.alexandre.userservice.service.auth;

import com.alexandre.userservice.dto.RegisterRequest;
import jakarta.validation.Valid;

public interface AuthService {
    void login(String email, String password);
    void register(RegisterRequest registerRequest);
}
