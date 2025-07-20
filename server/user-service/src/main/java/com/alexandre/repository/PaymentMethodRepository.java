package com.alexandre.repository;

import com.alexandre.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {
    boolean existsPaymentMethodByIdAndProfileId(UUID paymentMethodId, UUID profileId);

    List<PaymentMethod> findAllByProfileId(UUID profileId);
}
