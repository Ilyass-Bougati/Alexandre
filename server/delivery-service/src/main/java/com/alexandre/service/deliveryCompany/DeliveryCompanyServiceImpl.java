package com.alexandre.service.deliveryCompany;

import com.alexandre.dto.deliveryCompany.DeliveryCompanyDTO;
import com.alexandre.dto.deliveryCompany.DeliveryCompanyMapper;
import com.alexandre.exception.NotFoundException;
import com.alexandre.repository.DeliveryCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCompanyServiceImpl implements DeliveryCompanyService {

    private final DeliveryCompanyRepository deliveryCompanyRepository;
    private final DeliveryCompanyMapper deliveryCompanyMapper;

    @Override
    public DeliveryCompanyDTO create(DeliveryCompanyDTO deliveryCompanyDTO) {
        return deliveryCompanyMapper.toDto(deliveryCompanyRepository.save(deliveryCompanyMapper.toEntity(deliveryCompanyDTO)));
    }

    @Override
    public DeliveryCompanyDTO update(DeliveryCompanyDTO deliveryCompanyDTO) {
        return null;
    }

    @Override
    public DeliveryCompanyDTO findById(UUID id) {
        return deliveryCompanyRepository.findById(id)
                .map(deliveryCompanyMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Delivery Company not found"));
    }

    @Override
    public void delete(UUID id) {
        deliveryCompanyRepository.deleteById(id);
    }

    @Override
    public List<DeliveryCompanyDTO> findAll() {
        return deliveryCompanyRepository.findAll()
                .stream().map(deliveryCompanyMapper::toDto).toList();
    }
}
