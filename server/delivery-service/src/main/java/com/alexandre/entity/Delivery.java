package com.alexandre.entity;


import com.alexandre.enums.DeliveryState;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String recipientFirstName;

    @NotBlank
    private String recipientLastName;

    @NotBlank
    @Email
    private String recipientEmail;

    @NotBlank
    private String recipientPhone;

    @NotBlank
    private String productName;

    @NotNull
    @Min(value = 0)
    private Long productPrice;

    @NotNull
    private Boolean prePaid;

    @NotBlank
    private String street;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String notes;

    @NotNull
    private DeliveryState state;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private City city;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private DeliveryCompany company;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
}
