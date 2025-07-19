package com.alexandre.service.city;

import com.alexandre.dto.city.CityDTO;
import com.alexandre.dto.city.CityMapper;
import com.alexandre.event.CityCreatedEvent;
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
    private final KafkaTemplate<String, CityCreatedEvent> kafkaTemplate;

    @Override
    public CityDTO create(CityDTO cityDTO) {
        CityDTO city = cityMapper.toDto(cityRepository.save(cityMapper.toEntity(cityDTO)));

        // producing and event
        CityCreatedEvent event = CityCreatedEvent.builder()
                .name(city.getName())
                .shippingFee(city.getShippingFee())
                .id(city.getId())
                .build();

        kafkaTemplate.send("city.created", event);

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
    }

    @Override
    public List<CityDTO> findAll() {
        return cityRepository.findAll()
                .stream().map(cityMapper::toDto).toList();
    }
}
