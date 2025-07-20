package com.alexandre.service.deliveryCompany;

import com.alexandre.dto.deliveryCompany.DeliveryCompanyDTO;
import com.alexandre.service.CrudService;

import java.util.UUID;

public interface DeliveryCompanyService extends CrudService<DeliveryCompanyDTO, UUID> {
    DeliveryCompanyDTO addAvailableCity(UUID companyId, UUID cityId);
    DeliveryCompanyDTO removeAvailableCity(UUID companyId, UUID cityId);
}
