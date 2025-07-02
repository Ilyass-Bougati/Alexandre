package com.alexandre.inventoryservice.mapper.implementation;

import com.alexandre.inventoryservice.dto.UnitDTO;
import com.alexandre.inventoryservice.entity.Unit;
import com.alexandre.inventoryservice.mapper.UnitMapper;
import org.springframework.stereotype.Service;

@Service
public class UnitMapperImpl implements UnitMapper {
    @Override
    public UnitDTO toDto(Unit unit) {
        return UnitDTO.builder()
                .id(unit.getId())
                .state(unit.getState())
                .warehouseId(unit.getWarehouse().getId())
                .serialNumber(unit.getSerialNumber())
                .build();
    }

    // TODO : make this use the warehouseService to get the warehouse
    @Override
    public Unit toEntity(UnitDTO unitDTO) {
        return Unit.builder()
                .state(unitDTO.getState())
                .warehouse(null)
                .serialNumber(unitDTO.getSerialNumber())
                .build();
    }
}
