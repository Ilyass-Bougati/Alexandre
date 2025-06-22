package com.alexandre.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String street;

    @NotBlank
    private String postalCode;

    @NotNull
    private Boolean isDefault;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="profile_id", nullable=false)
    private Profile profile;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="city_id", nullable=false)
    private City city;
}
