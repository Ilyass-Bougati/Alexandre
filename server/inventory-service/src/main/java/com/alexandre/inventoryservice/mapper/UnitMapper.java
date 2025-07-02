package com.alexandre.inventoryservice.mapper;

import com.alexandre.inventoryservice.dto.UnitDTO;
import com.alexandre.inventoryservice.entity.Unit;
import org.mapstruct.Mapper;

@Mapper
public interface UnitMapper extends MapperInterface<Unit, UnitDTO> {
}
