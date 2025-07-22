package com.alexandre.controller;

import com.alexandre.dto.city.CityDTO;
import com.alexandre.service.city.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery-city/api/v1")
public class CityController {

    private final CityService cityService;

    @GetMapping("/")
    public ResponseEntity<List<CityDTO>> findAll() {
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> findCityById(@PathVariable UUID id) {
        return ResponseEntity.ok(cityService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<CityDTO> createCity(@RequestBody @Valid CityDTO city) {
        return ResponseEntity.ok(cityService.create(city));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCity(@PathVariable UUID id) {
        cityService.delete(id);
    }
}
