package com.alexandre.userservice.service;

public interface EntityService<Entity, ID> {
    Entity findById(ID id);
}
