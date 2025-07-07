package com.alexandre.userservice;

import com.alexandre.userservice.record.JwtConverterProperties;
import com.alexandre.userservice.record.KeycloakProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({
        JwtConverterProperties.class,
        KeycloakProperties.class
})
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
