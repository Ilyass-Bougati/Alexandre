package com.alexandre.inventoryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "The name can't be null or empty")
    private String name;

    @NotEmpty(message = "The phone number can't be null or empty")
    private String phoneNumber;

    @NotEmpty(message = "The email can't be null or empty")
    @Email(message = "The email should follow the standard pattern")
    private String email;

    @NotEmpty(message = "The address can't be null or empty")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductVendor> products;
}
