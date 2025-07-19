package com.alexandre.listener;

import com.alexandre.event.RegisterEvent;
import com.alexandre.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// TODO : remove the comments, I only added them in dev to not send unnecessary emails

@Component
@Slf4j
@RequiredArgsConstructor
public class RegisterEventHandler {

    private final EmailService emailService;

//    @KafkaListener(topics = "user.register")
//    public void processUserRegisterEvent(RegisterEvent event) {
//        emailService.send(event.getEmail(), "Welcome to Alexandre", "welcome.html");
//        log.info("User registered: {}, {}", event.getProfileId(), event.getEmail());
//    }

}
