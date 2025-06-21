package com.alexandre.userservice.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    @NotBlank @NotNull
    private String firstName;
    @NotBlank @NotNull
    private String lastName;
    @NotBlank @NotNull @Email
    private String email;
    @NotBlank @NotNull
    private String phoneNumber;
    @NotBlank @NotNull
    private String address;
    @NotBlank @NotNull
    private String password;
}
