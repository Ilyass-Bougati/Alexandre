package com.alexandre.userservice.service.paymentMethod;

import com.alexandre.userservice.dto.PaymentMethodDTO;
import com.alexandre.userservice.service.CrudService;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodService extends CrudService<PaymentMethodDTO, UUID> {
    boolean profileOwnsPaymentMethod(UUID profileId, UUID paymentMethodId);
    List<PaymentMethodDTO> findByProfileId(UUID profileId);
}
