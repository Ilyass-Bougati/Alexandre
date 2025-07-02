package com.alexandre.inventoryservice.service.implementation;

import com.alexandre.inventoryservice.dto.UnitDTO;
import com.alexandre.inventoryservice.entity.Unit;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.mapper.UnitMapper;
import com.alexandre.inventoryservice.repository.UnitRepository;
import com.alexandre.inventoryservice.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;


    @Override
    @Transactional(readOnly = true)
    public UnitDTO findById(UUID id) {
        return unitRepository.findById(id)
                .map(unitMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Unit not found"));
    }

    @Override
    public UnitDTO create(UnitDTO unitDTO) {
        Unit unit = unitMapper.toEntity(unitDTO);
        return unitMapper.toDto(unitRepository.save(unit));
    }

    @Override
    public UnitDTO update(UnitDTO unitDTO) {
        Unit oldUnitOptional = unitRepository.findById(unitDTO.getId())
                .orElseThrow(() -> new NotFoundException("Unit not found"));

        // TODO : add changing the warehouse
        oldUnitOptional.setState(unitDTO.getState());
        oldUnitOptional.setSerialNumber(unitDTO.getSerialNumber());

        unitRepository.save(oldUnitOptional);
        return unitMapper.toDto(oldUnitOptional);
    }

    @Override
    public void deleteById(UUID uuid) {
        unitRepository.deleteById(uuid);
    }
}
