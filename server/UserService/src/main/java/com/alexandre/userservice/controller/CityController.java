package com.alexandre.userservice.controller;

import com.alexandre.userservice.dto.CityDTO;
import com.alexandre.userservice.service.city.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/city/api/v1")
public class CityController {
    private final CityService cityService;

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCity(@PathVariable UUID id) {
        return ResponseEntity.ok(cityService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<CityDTO> createCity(@RequestBody @Valid CityDTO cityDTO) {
        return ResponseEntity.ok(cityService.create(cityDTO));
    }

    @PutMapping("/")
    public ResponseEntity<CityDTO> updateCity(@RequestBody @Valid CityDTO cityDTO) {
        return ResponseEntity.ok(cityService.update(cityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable UUID id) {
        cityService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
