package com.alexandre.userservice.dto.mapper.Implementation;

import com.alexandre.userservice.dto.PaymentMethodDTO;
import com.alexandre.userservice.dto.mapper.PaymentMethodMapper;
import com.alexandre.userservice.entity.PaymentMethod;
import com.alexandre.userservice.service.profile.ProfileEntityService;
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
                .stripeToken(paymentMethod.getStripeToken())
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
                .stripeToken(paymentMethodDTO.getStripeToken())
                .profile(profileEntityService.findById(paymentMethodDTO.getProfileId()))
                .createdAt(paymentMethodDTO.getCreatedAt())
                .build();
    }
}
