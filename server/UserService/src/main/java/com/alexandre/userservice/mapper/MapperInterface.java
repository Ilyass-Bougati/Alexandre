package com.alexandre.userservice.mapper;

import org.mapstruct.Mapper;

public interface MapperInterface<Entity, Dto> {
    Dto toDto(Entity entity);
    Entity toEntity(Dto dto);
}
