package com.alexandre.controller;

import com.alexandre.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final EmailService emailService;

    @PostMapping("/")
    public String test() {
        emailService.send("i.bougati12@gmail.com", "test email", "this is a test email");
        return "success";
    }
}
