package com.alexandre.dto.delivery;

import com.alexandre.entity.Delivery;
import org.springframework.stereotype.Service;

@Service
public class DeliveryMapperImpl implements DeliveryMapper {
    @Override
    public DeliveryDTO toDto(Delivery delivery) {
        DeliveryDTO dto = DeliveryDTO.builder()
                .notes(delivery.getNotes())
                .id(delivery.getId())
                .prePaid(delivery.getPrePaid())
                .createdAt(delivery.getCreatedAt())
                .deliveredAt(delivery.getDeliveredAt())
                .recipientEmail(delivery.getRecipientEmail())
                .recipientPhone(delivery.getRecipientPhone())
                .recipientFirstName(delivery.getRecipientFirstName())
                .recipientLastName(delivery.getRecipientLastName())
                .street(delivery.getStreet())
                .postalCode(delivery.getPostalCode())
                .productName(delivery.getProductName())
                .productPrice(delivery.getProductPrice())
                .state(delivery.getState())
                .build();

        if (delivery.getCity() != null) {
            dto.setCityId(delivery.getCity().getId());
        }

        if (delivery.getCompany() != null) {
            dto.setCompanyId(delivery.getCompany().getId());
        }

        return dto;
    }

    @Override
    public Delivery toEntity(DeliveryDTO deliveryDTO) {
        // TODO : Add the city and company later
        return Delivery.builder()
                .notes(deliveryDTO.getNotes())
                .id(deliveryDTO.getId())
                .prePaid(deliveryDTO.getPrePaid())
                .createdAt(deliveryDTO.getCreatedAt())
                .deliveredAt(deliveryDTO.getDeliveredAt())
                .recipientEmail(deliveryDTO.getRecipientEmail())
                .recipientPhone(deliveryDTO.getRecipientPhone())
                .recipientFirstName(deliveryDTO.getRecipientFirstName())
                .recipientLastName(deliveryDTO.getRecipientLastName())
                .street(deliveryDTO.getStreet())
                .postalCode(deliveryDTO.getPostalCode())
                .productName(deliveryDTO.getProductName())
                .productPrice(deliveryDTO.getProductPrice())
                .state(deliveryDTO.getState())
                .build();
    }
}
