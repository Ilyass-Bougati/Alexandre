package com.alexandre.dto.deliveryCompany;

import com.alexandre.entity.City;
import com.alexandre.entity.DeliveryCompany;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DeliveryCompanyMapperImpl implements DeliveryCompanyMapper {
    @Override
    public DeliveryCompanyDTO toDto(DeliveryCompany deliveryCompany) {
        DeliveryCompanyDTO dto = DeliveryCompanyDTO.builder()
                .email(deliveryCompany.getEmail())
                .name(deliveryCompany.getName())
                .availableCitiesIds(new ArrayList<>())
                .id(deliveryCompany.getId())
                .build();

        for (City city : deliveryCompany.getAvailableCities()) {
            dto.getAvailableCitiesIds().add(city.getId());
        }

        return dto;
    }

    @Override
    public DeliveryCompany toEntity(DeliveryCompanyDTO deliveryCompanyDTO) {
        // TODO : use the city service to get the cities
        return DeliveryCompany.builder()
                .email(deliveryCompanyDTO.getEmail())
                .name(deliveryCompanyDTO.getName())
                .availableCities(new ArrayList<>())
                .id(deliveryCompanyDTO.getId())
                .build();
    }
}
