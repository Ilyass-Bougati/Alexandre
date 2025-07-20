package com.alexandre.service.email;

public interface EmailService {
    void send(String to, String subject, String templatePath);
}
