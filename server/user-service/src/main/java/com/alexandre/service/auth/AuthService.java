package com.alexandre.service.auth;

import com.alexandre.dto.RegisterRequest;

public interface AuthService {
    void login(String email, String password);
    void register(RegisterRequest registerRequest);
}
