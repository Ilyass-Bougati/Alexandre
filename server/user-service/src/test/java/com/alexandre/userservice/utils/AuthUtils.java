package com.alexandre.userservice.utils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class AuthUtils {
    public static MultiValueMap<String, String> registerFormData(String email, String password) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", email);
        formData.add("password", password);
        formData.add("grant_type", "password");
        formData.add("client_id", "public-client");

        return formData;
    }
}
