package com.alexandre.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @NotNull
    private String password;

    @Column(unique = true)
    @NotBlank
    @NotNull
    private String phoneNumber;

    @NotBlank
    @NotNull
    private String address;
}
