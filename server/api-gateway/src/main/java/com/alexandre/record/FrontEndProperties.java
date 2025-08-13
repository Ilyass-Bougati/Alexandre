package com.alexandre.record;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "front-end")
public record FrontEndProperties(String url) {
}
