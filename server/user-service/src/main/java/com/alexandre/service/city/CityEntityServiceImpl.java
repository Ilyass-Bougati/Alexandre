package com.alexandre.service.city;

import com.alexandre.entity.City;
import com.alexandre.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class CityEntityServiceImpl implements CityEntityService {
    private final CityRepository cityRepository;

    @Override
    public City findById(UUID uuid) {
        return cityRepository.findById(uuid)
                .orElse(null);
    }
}
