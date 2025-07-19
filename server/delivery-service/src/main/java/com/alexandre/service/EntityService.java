package com.alexandre.service;

public interface EntityService <ENTITY, ID> {
    ENTITY findById(ID id);
}
