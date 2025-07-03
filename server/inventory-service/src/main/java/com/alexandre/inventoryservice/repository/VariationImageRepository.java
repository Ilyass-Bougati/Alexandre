package com.alexandre.inventoryservice.repository;

import com.alexandre.inventoryservice.entity.VariationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VariationImageRepository extends JpaRepository<VariationImage, UUID> {
}
