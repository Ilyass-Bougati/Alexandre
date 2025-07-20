package com.alexandre.service.address;

import com.alexandre.dto.AddressDTO;
import com.alexandre.service.CrudService;

import java.util.List;
import java.util.UUID;

public interface AddressService extends CrudService<AddressDTO, UUID> {
    List<AddressDTO> findByProfileId(UUID profileId);
    Boolean profileOwnsAddress(UUID profileId, UUID addressId);
}
