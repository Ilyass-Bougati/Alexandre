package com.alexandre.userservice.service.address;

import com.alexandre.userservice.dto.AddressDTO;
import com.alexandre.userservice.service.CrudService;

import java.util.List;
import java.util.UUID;

public interface AddressService extends CrudService<AddressDTO, UUID> {
    List<AddressDTO> findByProfileId(UUID profileId);
    Boolean profileOwnsAddress(UUID profileId, UUID addressId);
}
