package com.alexandre.controller;

import com.alexandre.dto.CityDTO;
import com.alexandre.service.city.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/by-name/{name}")
    public ResponseEntity<CityDTO> getCityByName(@PathVariable String name) {
        return ResponseEntity.ok(cityService.findByName(name));
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('alex_admin')")
    public ResponseEntity<CityDTO> createCity(@RequestBody @Valid CityDTO cityDTO) {
        return ResponseEntity.ok(cityService.create(cityDTO));
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('alex_admin')")
    public ResponseEntity<CityDTO> updateCity(@RequestBody @Valid CityDTO cityDTO) {
        return ResponseEntity.ok(cityService.update(cityDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('alex_admin')")
    public ResponseEntity<Void> deleteCity(@PathVariable UUID id) {
        cityService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
