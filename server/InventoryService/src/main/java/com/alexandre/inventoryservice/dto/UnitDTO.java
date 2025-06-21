package com.alexandre.inventoryservice.dto;

import com.alexandre.inventoryservice.enums.UnitState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer serialNumber;
    @NotNull
    private UnitState state;
    @NotNull
    private UUID warehouseId;
}
