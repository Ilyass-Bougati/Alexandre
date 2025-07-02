package com.alexandre.userservice.dto.mapper;


public interface MapperInterface<Entity, Dto> {
    Dto toDto(Entity entity);
    Entity toEntity(Dto dto);
}
