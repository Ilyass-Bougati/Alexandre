package com.alexandre.userservice.record;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeycloakProperties(String realm, String clientId, String clientSecret, String adminUsername, String adminPassword, String url) {
}
