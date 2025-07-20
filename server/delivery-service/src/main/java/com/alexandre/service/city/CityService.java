package com.alexandre.service.city;

import com.alexandre.dto.city.CityDTO;
import com.alexandre.service.CrudService;

import java.util.UUID;

public interface CityService extends CrudService<CityDTO, UUID> {
    void createSilent(CityDTO dto);
    void deleteSilent(UUID id);
}
