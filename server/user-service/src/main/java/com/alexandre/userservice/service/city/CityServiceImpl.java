package com.alexandre.userservice.service.city;

import com.alexandre.userservice.dto.CityDTO;
import com.alexandre.userservice.dto.mapper.CityMapper;
import com.alexandre.userservice.entity.City;
import com.alexandre.userservice.event.CityCreatedEvent;
import com.alexandre.userservice.event.CityDeletedEvent;
import com.alexandre.userservice.exception.NotFoundException;
import com.alexandre.userservice.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, CityCreatedEvent> cityCreatedKafkaTemplate;
    private final KafkaTemplate<String, CityDeletedEvent> cityDeletedKafkaTemplate;

    @Override
    @Transactional(readOnly = true)
    public CityDTO findById(UUID id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        return cityOptional.map(cityMapper::toDto)
                .orElseThrow(() -> new NotFoundException("City not found"));
    }

    @Override
    public CityDTO create(CityDTO cityDTO) {
        // Generating a random UUID for the id of the city
        cityDTO.setId(UUID.randomUUID());

        CityDTO city = cityMapper.toDto(cityRepository.save(cityMapper.toEntity(cityDTO)));

        // producing and event
        CityCreatedEvent event = CityCreatedEvent.builder()
                .name(city.getName())
                .shippingFee(city.getShippingFee())
                .id(city.getId())
                .build();

        cityCreatedKafkaTemplate.send("city.created", event);

        return city;
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

        // producing an event
        CityDeletedEvent event = CityDeletedEvent.builder()
                .cityId(id)
                .build();

        cityDeletedKafkaTemplate.send("city.deleted", event);
    }

    @Override
    public CityDTO findByName(String name) {
        return cityRepository.findCityByName(name)
                .map(cityMapper::toDto)
                .orElseThrow(() -> new NotFoundException("City not found"));
    }

    /**
     * This function creates a city and saves it to the database without producing a kafka event
     * @param cityDTO The city object to create
     */
    @Override
    public void createSilent(CityDTO cityDTO) {
        if (cityDTO.getId() == null) {
            // Generating a random UUID for the id of the city
            cityDTO.setId(UUID.randomUUID());
        }
        cityRepository.save(cityMapper.toEntity(cityDTO));
    }

    /**
     * This function deletes a city from database without producing a kafka event
     * @param id The id city of the city to delete
     */
    @Override
    public void deleteSilent(UUID id) {
        cityRepository.deleteById(id);
    }
}
