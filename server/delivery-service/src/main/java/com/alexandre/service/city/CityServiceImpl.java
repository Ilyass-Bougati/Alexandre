package com.alexandre.service.city;

import com.alexandre.dto.city.CityDTO;
import com.alexandre.dto.city.CityMapper;
import com.alexandre.event.CityCreatedEvent;
import com.alexandre.event.CityDeletedEvent;
import com.alexandre.exception.NotFoundException;
import com.alexandre.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final KafkaTemplate<String, CityCreatedEvent> cityCreatedKafkaTemplate;
    private final KafkaTemplate<String, CityDeletedEvent> cityDeletedKafkaTemplate;

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
        return null;
    }

    @Override
    public CityDTO findById(UUID id) {
        return cityRepository.findById(id)
                .map(cityMapper::toDto)
                .orElseThrow(() -> new NotFoundException("City not found"));
    }

    @Override
    public void delete(UUID id) {

        cityRepository.deleteById(id);

        // producing an event
        CityDeletedEvent event = CityDeletedEvent.builder()
                .cityId(id)
                .build();

        cityDeletedKafkaTemplate.send("city.deleted", event);
    }

    @Override
    public List<CityDTO> findAll() {
        return cityRepository.findAll()
                .stream().map(cityMapper::toDto).toList();
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
