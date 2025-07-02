package com.alexandre.userservice.service.city;

import com.alexandre.userservice.dto.CityDTO;
import com.alexandre.userservice.dto.mapper.CityMapper;
import com.alexandre.userservice.entity.City;
import com.alexandre.userservice.exception.NotFoundException;
import com.alexandre.userservice.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    @Transactional(readOnly = true)
    public CityDTO findById(UUID id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        return cityOptional.map(cityMapper::toDto)
                .orElseThrow(() -> new NotFoundException("City not found"));
    }

    @Override
    public CityDTO create(CityDTO cityDTO) {
        City city = cityMapper.toEntity(cityDTO);
        return cityMapper.toDto(cityRepository.save(city));
    }

    @Override
    public CityDTO update(CityDTO cityDTO) {
        City oldCityOptional = cityRepository.findById(cityDTO.getId())
                .orElseThrow(() -> new NotFoundException("City not found"));

        oldCityOptional.setName(cityDTO.getName());
        oldCityOptional.setShippingFee(cityDTO.getShippingFee());

        cityRepository.save(oldCityOptional);
        return cityMapper.toDto(oldCityOptional);
    }

    @Override
    public void deleteById(UUID id) {
        cityRepository.deleteById(id);
    }
}
