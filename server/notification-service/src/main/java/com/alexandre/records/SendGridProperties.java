package com.alexandre.records;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sendgrid")
public record SendGridProperties(String apiKey, String fromEmail) {
}
