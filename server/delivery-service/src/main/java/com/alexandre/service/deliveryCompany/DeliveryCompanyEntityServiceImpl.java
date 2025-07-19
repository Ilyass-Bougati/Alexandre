package com.alexandre.service.deliveryCompany;

import com.alexandre.entity.DeliveryCompany;
import com.alexandre.exception.NotFoundException;
import com.alexandre.repository.DeliveryCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCompanyEntityServiceImpl implements DeliveryCompanyEntityService {

    private final DeliveryCompanyRepository deliveryCompanyRepository;

    @Override
    public DeliveryCompany findById(UUID uuid) {
        return deliveryCompanyRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Delivery Company not found"));
    }
}
