package com.alexandre.notificationservice;

import com.alexandre.notificationservice.event.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notification")
    public void NotificationEventHandler(NotificationEvent notificationEvent) {
        log.info("Message {} for {}", notificationEvent.getMessage(), notificationEvent.getProfileId());
    }
}
