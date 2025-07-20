package com.alexandre.service.delivery;

import com.alexandre.dto.delivery.DeliveryDTO;
import com.alexandre.dto.delivery.DeliveryMapper;
import com.alexandre.entity.Delivery;
import com.alexandre.exception.NotFoundException;
import com.alexandre.repository.DeliveryRepository;
import com.alexandre.service.city.CityEntityService;
import com.alexandre.service.deliveryCompany.DeliveryCompanyEntityService;
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
    private final CityEntityService cityEntityService;
    private final DeliveryCompanyEntityService deliveryCompanyEntityService;

    @Override
    public DeliveryDTO create(DeliveryDTO deliveryDTO) {
        return deliveryMapper.toDto(deliveryRepository.save(deliveryMapper.toEntity(deliveryDTO)));
    }

    @Override
    public DeliveryDTO update(DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryRepository.findById(deliveryDTO.getId())
                .orElseThrow(() -> new NotFoundException("Delivery not found"));

        delivery.setNotes(deliveryDTO.getNotes());
        delivery.setPostalCode(deliveryDTO.getPostalCode());
        delivery.setPrePaid(deliveryDTO.getPrePaid());
        delivery.setProductPrice(deliveryDTO.getProductPrice());
        delivery.setRecipientEmail(deliveryDTO.getRecipientEmail());
        delivery.setRecipientFirstName(deliveryDTO.getRecipientFirstName());
        delivery.setRecipientLastName(deliveryDTO.getRecipientLastName());
        delivery.setRecipientPhone(deliveryDTO.getRecipientPhone());
        delivery.setState(deliveryDTO.getState());
        delivery.setStreet(deliveryDTO.getStreet());
        delivery.setCity(cityEntityService.findById(deliveryDTO.getCityId()));
        delivery.setCompany(deliveryCompanyEntityService.findById(deliveryDTO.getCompanyId()));

        return deliveryMapper.toDto(delivery);
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
