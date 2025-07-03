package com.alexandre.inventoryservice.repository;

import com.alexandre.inventoryservice.entity.ProductVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductVendorRepository extends JpaRepository<ProductVendor, UUID> {
}
