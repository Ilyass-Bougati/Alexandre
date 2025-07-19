package com.alexandre.service.delivery;

import com.alexandre.dto.delivery.DeliveryDTO;
import com.alexandre.dto.delivery.DeliveryMapper;
import com.alexandre.exception.NotFoundException;
import com.alexandre.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    @Override
    public DeliveryDTO create(DeliveryDTO deliveryDTO) {
        return deliveryMapper.toDto(deliveryRepository.save(deliveryMapper.toEntity(deliveryDTO)));
    }

    @Override
    public DeliveryDTO update(DeliveryDTO deliveryDTO) {
        return null;
    }

    @Override
    public DeliveryDTO findById(UUID id) {
        return deliveryRepository.findById(id)
                .map(deliveryMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Delivery not found"));
    }

    @Override
    public void delete(UUID id) {
        deliveryRepository.deleteById(id);
    }

    @Override
    public List<DeliveryDTO> findAll() {
        return deliveryRepository.findAll()
                .stream().map(deliveryMapper::toDto).toList();
    }
}
