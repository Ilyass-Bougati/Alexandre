package com.alexandre.inventoryservice.mapper;

public interface MapperInterface<Entity, Dto> {
    Dto toDto(Entity entity);
    Entity toEntity(Dto dto);
}

