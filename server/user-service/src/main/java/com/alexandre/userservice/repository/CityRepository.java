package com.alexandre.userservice.repository;

import com.alexandre.userservice.dto.CityDTO;
import com.alexandre.userservice.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
    Optional<City> findCityByName(String name);
}
