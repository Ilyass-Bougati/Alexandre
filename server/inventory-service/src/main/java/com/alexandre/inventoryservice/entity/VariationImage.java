package com.alexandre.inventoryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "variation_images")
public class VariationImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "The uri can't be empty")
    private String uri;

    @NotNull(message = "The order index can't be null")
    @Min(value = 0, message = "The order index can't be negative")
    private Integer orderIndex;

    @Builder.Default
    private Boolean thumbnail = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="variation_id", nullable=false)
    private ProductVariation variation;
}
