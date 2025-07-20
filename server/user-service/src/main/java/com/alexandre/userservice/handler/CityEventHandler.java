package com.alexandre.userservice.handler;

import com.alexandre.userservice.dto.CityDTO;
import com.alexandre.userservice.event.CityCreatedEvent;
import com.alexandre.userservice.event.CityDeletedEvent;
import com.alexandre.userservice.service.city.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CityEventHandler {

    private final CityService cityService;

    @KafkaListener(topics = "city.created")
    public void cityCreatedEventHandler(CityCreatedEvent cityCreatedEvent) {
        CityDTO cityDTO = CityDTO.builder()
                .name(cityCreatedEvent.getName())
                .id(cityCreatedEvent.getId())
                .shippingFee(cityCreatedEvent.getShippingFee())
                .build();

        cityService.createSilent(cityDTO);
        log.info("City created: {}", cityDTO.getName());
    }

    @KafkaListener(topics = "city.deleted")
    public void cityDeletedEventHandler(CityDeletedEvent cityDeletedEvent) {
        cityService.deleteSilent(cityDeletedEvent.getCityId());
        log.info("City deleted: {}", cityDeletedEvent.getCityId());
    }

}
