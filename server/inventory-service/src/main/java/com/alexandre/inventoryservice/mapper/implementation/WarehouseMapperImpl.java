package com.alexandre.inventoryservice.mapper.implementation;

import com.alexandre.inventoryservice.dto.WarehouseDTO;
import com.alexandre.inventoryservice.entity.Warehouse;
import com.alexandre.inventoryservice.mapper.WarehouseMapper;
import org.springframework.stereotype.Service;

@Service
public class WarehouseMapperImpl implements WarehouseMapper {
    @Override
    public WarehouseDTO toDto(Warehouse warehouse) {
        return WarehouseDTO.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .build();
    }

    @Override
    public Warehouse toEntity(WarehouseDTO warehouseDTO) {
        return Warehouse.builder()
                .name(warehouseDTO.getName())
                .build();
    }
}
