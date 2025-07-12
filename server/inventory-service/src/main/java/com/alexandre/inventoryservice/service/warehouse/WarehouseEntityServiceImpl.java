package com.alexandre.inventoryservice.service.warehouse;

import com.alexandre.inventoryservice.entity.Warehouse;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WarehouseEntityServiceImpl implements WarehouseEntityService {

    private final WarehouseRepository warehouseRepository;

    @Override
    public Warehouse findById(UUID id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Warehouse not found"));
    }
}
