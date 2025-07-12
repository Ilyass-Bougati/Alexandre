package com.alexandre.inventoryservice.service.warehouse;

import com.alexandre.inventoryservice.dto.WarehouseDTO;
import com.alexandre.inventoryservice.entity.Warehouse;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.mapper.WarehouseMapper;
import com.alexandre.inventoryservice.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;


    @Override
    @Transactional(readOnly = true)
    public WarehouseDTO findById(UUID id) {
        return warehouseRepository.findById(id)
                .map(warehouseMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Warehouse not found"));
    }

    @Override
    public WarehouseDTO create(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDTO);
        return warehouseMapper.toDto(warehouseRepository.save(warehouse));
    }

    /**
     * Note that this doesn't change the variations
     * @param warehouseDTO the new warehouse data, the id should be provided
     * @return the updated warehouse data
     */
    @Override
    public WarehouseDTO update(WarehouseDTO warehouseDTO) {
        Warehouse oldWarehouseOptional = warehouseRepository.findById(warehouseDTO.getId())
                .orElseThrow(() -> new NotFoundException("Warehouse not found"));

        oldWarehouseOptional.setName(warehouseDTO.getName());
        oldWarehouseOptional.setLocation(warehouseDTO.getLocation());

        warehouseRepository.save(oldWarehouseOptional);
        return warehouseMapper.toDto(oldWarehouseOptional);
    }

    @Override
    public void deleteById(UUID uuid) {
        warehouseRepository.deleteById(uuid);
    }
}
