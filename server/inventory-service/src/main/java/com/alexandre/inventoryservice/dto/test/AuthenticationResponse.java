package com.alexandre.inventoryservice.dto.test;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AuthenticationResponse {
    String access_token;
    String refresh_token;
    String token_type;
    String session_state;
    String scope;
    Integer expires_in;
    Integer refresh_expires_in;
}
