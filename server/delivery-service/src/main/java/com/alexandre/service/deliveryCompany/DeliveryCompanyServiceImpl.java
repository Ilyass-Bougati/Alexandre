package com.alexandre.service.deliveryCompany;

import com.alexandre.dto.deliveryCompany.DeliveryCompanyDTO;
import com.alexandre.dto.deliveryCompany.DeliveryCompanyMapper;
import com.alexandre.entity.City;
import com.alexandre.entity.DeliveryCompany;
import com.alexandre.exception.NotFoundException;
import com.alexandre.repository.DeliveryCompanyRepository;
import com.alexandre.service.city.CityEntityService;
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
    private final CityEntityService cityEntityService;

    @Override
    public DeliveryCompanyDTO create(DeliveryCompanyDTO deliveryCompanyDTO) {
        return deliveryCompanyMapper.toDto(deliveryCompanyRepository.save(deliveryCompanyMapper.toEntity(deliveryCompanyDTO)));
    }

    @Override
    public DeliveryCompanyDTO update(DeliveryCompanyDTO deliveryCompanyDTO) {
        DeliveryCompany deliveryCompany = deliveryCompanyRepository.findById(deliveryCompanyDTO.getId())
                .orElseThrow(() -> new NotFoundException("Delivery Company not found"));

        deliveryCompany.setEmail(deliveryCompanyDTO.getEmail());
        deliveryCompany.setName(deliveryCompanyDTO.getName());
        deliveryCompany.setPhoneNumber(deliveryCompanyDTO.getPhoneNumber());

        return deliveryCompanyMapper.toDto(deliveryCompany);
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

    @Override
    public DeliveryCompanyDTO addAvailableCity(UUID companyId, UUID cityId) {
        DeliveryCompany deliveryCompany = deliveryCompanyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Delivery Company not found"));
        City city = cityEntityService.findById(cityId);

        deliveryCompany.getAvailableCities().add(city);

        return deliveryCompanyMapper.toDto(deliveryCompany);
    }

    @Override
    public DeliveryCompanyDTO removeAvailableCity(UUID companyId, UUID cityId) {
        DeliveryCompany deliveryCompany = deliveryCompanyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Delivery Company not found"));
        City city = cityEntityService.findById(cityId);

        deliveryCompany.getAvailableCities().remove(city);

        return deliveryCompanyMapper.toDto(deliveryCompany);
    }
}
