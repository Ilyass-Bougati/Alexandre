package com.alexandre.service.paymentMethod;

import com.alexandre.dto.PaymentMethodDTO;
import com.alexandre.service.CrudService;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodService extends CrudService<PaymentMethodDTO, UUID> {
    boolean profileOwnsPaymentMethod(UUID profileId, UUID paymentMethodId);
    List<PaymentMethodDTO> findByProfileId(UUID profileId);
}
