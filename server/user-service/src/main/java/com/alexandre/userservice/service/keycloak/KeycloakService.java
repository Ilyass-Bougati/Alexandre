package com.alexandre.userservice.service.keycloak;

import com.alexandre.userservice.record.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KeycloakService {

    private final Keycloak keycloakClient;
    private final String keycloakRealm;


    public KeycloakService(KeycloakProperties keycloakProperties) {
        this.keycloakRealm = keycloakProperties.realm();
        this.keycloakClient = KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.url())
                .realm(keycloakRealm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(keycloakProperties.clientId())
                .clientSecret(keycloakProperties.clientSecret())
                .username(keycloakProperties.adminUsername())
                .password(keycloakProperties.adminPassword())
                .build();
    }

    /**
     * This function creates a keycloak user
     * @param email the user's email
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param rawPassword the user's password
     * @return the user's id as a UUID
     */
    public UUID registerUser(String email, String firstName, String lastName, String rawPassword) {
        // Build the UserRepresentation payload
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(email); // using the email as a username
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailVerified(false);

        // Create the user
        RealmResource realmResource   = keycloakClient.realm(keycloakRealm);
        UsersResource usersResource   = realmResource.users();

        Response response = usersResource.create(user);

        // Handling exceptions
        if (response.getStatus() != 201) {
            throw new IllegalStateException("Keycloak user creation failed: " + response.getStatus());
        }

        // Extract the generated userId from the Location header
        String userId = UriBuilder
                .fromUri(response.getLocation())
                .build()
                .getPath()
                .replaceAll(".*/([^/]+)$", "$1");  // last path segment = UUID

        // Set the initial password
        CredentialRepresentation pw = new CredentialRepresentation();
        pw.setType(CredentialRepresentation.PASSWORD);
        pw.setTemporary(false);
        pw.setValue(rawPassword);

        usersResource.get(userId).resetPassword(pw);

        return UUID.fromString(userId);
    }
}
