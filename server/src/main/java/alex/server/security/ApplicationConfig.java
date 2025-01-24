package alex.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ApplicationConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(c -> c
                // this will be override through filters
                .anyRequest().permitAll()
        );
        http.csrf(c -> c.disable());
        http.addFilterAt(
                new CustomAuthenticationFilter(),
                BasicAuthenticationFilter.class
        );
        return http.build();
    }

}
