package com.alexandre.userservice.repository;

import com.alexandre.userservice.dto.AddressDTO;
import com.alexandre.userservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findAllByProfileId(UUID profileId);
    Boolean existsByProfileIdAndId(UUID profileId, UUID addressId);
}
