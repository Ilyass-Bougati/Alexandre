package com.alexandre.service;

public interface EntityService<Entity, ID> {
    Entity findById(ID id);
}
