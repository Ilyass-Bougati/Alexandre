package com.alexandre.userservice.service.auth;

import com.alexandre.userservice.dto.RegisterRequest;

public interface AuthService {
    void login(String email, String password);
    void register(RegisterRequest registerRequest);
}
