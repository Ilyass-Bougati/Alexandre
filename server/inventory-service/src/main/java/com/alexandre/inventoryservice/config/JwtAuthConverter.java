package com.alexandre.inventoryservice.config;


import com.alexandre.inventoryservice.dto.ProfileDTO;
import com.alexandre.inventoryservice.record.JwtConverterProperties;
import com.alexandre.inventoryservice.record.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();
    private final WebClient webClient;

    private final String principleAttribute;
    private final String resourceId;

    public JwtAuthConverter(WebClient.Builder webClientBuilder, JwtConverterProperties jwtConverterProperties) {
        this.webClient = webClientBuilder.build();
        principleAttribute = jwtConverterProperties.principleAttribute();
        resourceId = jwtConverterProperties.resourceId();
    }

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());

        ResponseEntity<ProfileDTO> profileDTO = webClient.get()
                .uri("http://user-service/profile/api/v1/")
                .header("Authorization", "Bearer " + jwt.getTokenValue())
                .retrieve()
                .toEntity(ProfileDTO.class)
                .block();

        // TODO : make this better
        if (!profileDTO.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Unsuccessful authentication");
        }

        UserPrincipal userPrincipal = new UserPrincipal(jwt.getSubject(), profileDTO.getBody());
        return new UsernamePasswordAuthenticationToken(userPrincipal, "N/A", authorities);
    }

    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (principleAttribute != null) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> realmAccess;
        Collection<String> resourceRoles;
        if (jwt.getClaim("realm_access") == null) {
            return Set.of();
        }
        realmAccess = jwt.getClaim("realm_access");

        resourceRoles = (Collection<String>) realmAccess.get("roles");

        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
