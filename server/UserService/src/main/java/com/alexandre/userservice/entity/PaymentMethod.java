package com.alexandre.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String cardHolderName;

    @NotBlank
    private String stripeToken;

    @NotBlank
    private String last4Digits;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="profile_id", nullable=false)
    private Profile profile;
}
