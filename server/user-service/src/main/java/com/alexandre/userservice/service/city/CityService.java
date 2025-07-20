package com.alexandre.userservice.service.city;

import com.alexandre.userservice.dto.CityDTO;
import com.alexandre.userservice.service.CrudService;

import java.util.UUID;

public interface CityService extends CrudService<CityDTO, UUID> {
    CityDTO findByName(String name);
    void createSilent(CityDTO dto);
    void deleteSilent(UUID id);
}
