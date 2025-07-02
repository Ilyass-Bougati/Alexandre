package com.alexandre.orderservice.service;

public interface EntityService<Entity, ID> {
    Entity findById(ID id);
}
