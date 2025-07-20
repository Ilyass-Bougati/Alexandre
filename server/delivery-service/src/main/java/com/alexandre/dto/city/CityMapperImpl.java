package com.alexandre.dto.city;

import com.alexandre.entity.City;
import org.springframework.stereotype.Service;

@Service
public class CityMapperImpl implements CityMapper {
    @Override
    public CityDTO toDto(City city) {
        return CityDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .shippingFee(city.getShippingFee())
                .build();
    }

    @Override
    public City toEntity(CityDTO cityDTO) {
        return City.builder()
                .id(cityDTO.getId())
                .name(cityDTO.getName())
                .shippingFee(cityDTO.getShippingFee())
                .build();
    }
}
