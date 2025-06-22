package com.alexandre.orderservice.dto;

public interface MapperInterface<Entity, Dto> {
    Entity toEntity(Dto dto);
    Dto toDto(Entity entity);
}
