package com.alexandre.records;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt-converter")
public record JwtConverterProperties (String principleAttribute, String resourceId) {
}
