package com.alexandre.orderservice.service;


public interface CrudService<DTO, ID> {
    DTO findById(ID id);
    DTO create(DTO dto);
    DTO update(DTO dto);
    void deleteById(ID id);
}
