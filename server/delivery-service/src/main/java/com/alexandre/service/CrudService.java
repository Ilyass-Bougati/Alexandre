package com.alexandre.service;

import java.util.List;

public interface CrudService <DTO, ID>{
    DTO create(DTO dto);
    DTO update(DTO dto);
    DTO findById(ID id);
    void delete(ID id);
    List<DTO> findAll();
}
