package com.alexandre.inventoryservice.service;

public interface EntityService<Entity, Id> {
    Entity findById(Id id);
}
