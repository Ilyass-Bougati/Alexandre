package com.alexandre.dto;

public interface EntityDtoMapper<ENTITY, DTO> {
    DTO toDto(ENTITY entity);
    ENTITY toEntity(DTO dto);
}
