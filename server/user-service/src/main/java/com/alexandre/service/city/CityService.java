package com.alexandre.service.city;

import com.alexandre.dto.CityDTO;
import com.alexandre.service.CrudService;

import java.util.UUID;

public interface CityService extends CrudService<CityDTO, UUID> {
    CityDTO findByName(String name);
    void createSilent(CityDTO dto);
    void deleteSilent(UUID id);
}
