package com.alexandre.config;


import com.alexandre.dto.response.ProfileDTO;
import com.alexandre.record.JwtConverterProperties;
import com.alexandre.record.UserPrincipal;
import com.alexandre.service.profile.ProfileService;
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
    private final ProfileService profileService;

    public JwtAuthConverter(WebClient.Builder webClientBuilder, JwtConverterProperties jwtConverterProperties, ProfileService profileService) {
        this.webClient = webClientBuilder.build();
        principleAttribute = jwtConverterProperties.principleAttribute();
        resourceId = jwtConverterProperties.resourceId();
        this.profileService = profileService;
    }

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());

        ProfileDTO profileDTO = profileService.get(jwt);

        UserPrincipal userPrincipal = new UserPrincipal(jwt, jwt.getSubject(), profileDTO);
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
