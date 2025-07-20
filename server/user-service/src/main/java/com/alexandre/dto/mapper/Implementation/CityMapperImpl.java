package com.alexandre.dto.mapper.Implementation;

import com.alexandre.dto.CityDTO;
import com.alexandre.dto.mapper.CityMapper;
import com.alexandre.entity.City;
import org.springframework.stereotype.Service;

@Service
public class CityMapperImpl implements CityMapper{
    @Override
    public CityDTO toDto(City city) {
        return CityDTO.builder()
                .name(city.getName())
                .id(city.getId())
                .shippingFee(city.getShippingFee())
                .createdAt(city.getCreatedAt())
                .build();
    }

    @Override
    public City toEntity(CityDTO cityDTO) {
        return City.builder()
                .id(cityDTO.getId())
                .name(cityDTO.getName())
                .shippingFee(cityDTO.getShippingFee())
                .createdAt(cityDTO.getCreatedAt())
                .build();
    }
}
