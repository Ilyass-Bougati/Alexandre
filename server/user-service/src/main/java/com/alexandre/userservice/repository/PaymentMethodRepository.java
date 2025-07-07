package com.alexandre.userservice.repository;

import com.alexandre.userservice.dto.PaymentMethodDTO;
import com.alexandre.userservice.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {
    boolean existsPaymentMethodByIdAndProfileId(UUID paymentMethodId, UUID profileId);

    List<PaymentMethod> findAllByProfileId(UUID profileId);
}
