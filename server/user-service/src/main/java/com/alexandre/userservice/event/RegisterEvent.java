package com.alexandre.userservice.event;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEvent {
    String email;
    String lastName;
    UUID profileId;
    LocalDateTime createdAt;
}
