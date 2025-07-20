package com.alexandre.dto.mapper.Implementation;

import com.alexandre.dto.PaymentMethodDTO;
import com.alexandre.dto.mapper.PaymentMethodMapper;
import com.alexandre.entity.PaymentMethod;
import com.alexandre.service.profile.ProfileEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentMethodMapperImpl implements PaymentMethodMapper {

    private final ProfileEntityService profileEntityService;

    @Override
    public PaymentMethodDTO toDto(PaymentMethod paymentMethod) {
        return PaymentMethodDTO.builder()
                .id(paymentMethod.getId())
                .cardHolderName(paymentMethod.getCardHolderName())
                .last4Digits(paymentMethod.getLast4Digits())
                .cardName(paymentMethod.getCardName())
                .profileId(paymentMethod.getProfile().getId())
                .createdAt(paymentMethod.getCreatedAt())
                .build();
    }

    @Override
    public PaymentMethod toEntity(PaymentMethodDTO paymentMethodDTO) {
        return PaymentMethod.builder()
                .id(paymentMethodDTO.getId())
                .cardHolderName(paymentMethodDTO.getCardHolderName())
                .last4Digits(paymentMethodDTO.getLast4Digits())
                .cardName(paymentMethodDTO.getCardName())
                .profile(profileEntityService.findById(paymentMethodDTO.getProfileId()))
                .createdAt(paymentMethodDTO.getCreatedAt())
                .build();
    }
}
