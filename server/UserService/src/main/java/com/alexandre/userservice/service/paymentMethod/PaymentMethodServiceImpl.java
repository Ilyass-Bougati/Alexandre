package com.alexandre.userservice.service.paymentMethod;

import com.alexandre.userservice.dto.PaymentMethodDTO;
import com.alexandre.userservice.dto.mapper.PaymentMethodMapper;
import com.alexandre.userservice.entity.PaymentMethod;
import com.alexandre.userservice.exception.NotFoundException;
import com.alexandre.userservice.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    @Override
    @Transactional(readOnly = true)
    public PaymentMethodDTO findById(UUID id) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        return paymentMethodOptional.map(paymentMethodMapper::toDto)
                .orElseThrow(() -> new NotFoundException("PaymentMethod not found"));
    }

    @Override
    public PaymentMethodDTO create(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = paymentMethodMapper.toEntity(paymentMethodDTO);
        return paymentMethodMapper.toDto(paymentMethodRepository.save(paymentMethod));
    }

    @Override
    public PaymentMethodDTO update(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod oldPaymentMethodOptional = paymentMethodRepository.findById(paymentMethodDTO.getId())
                .orElseThrow(() -> new NotFoundException("PaymentMethod not found"));

        // TODO : rethink this
        paymentMethodDTO.setCardHolderName(paymentMethodDTO.getCardHolderName());
        paymentMethodDTO.setStripeToken(paymentMethodDTO.getStripeToken());
        paymentMethodDTO.setLast4Digits(paymentMethodDTO.getLast4Digits());

        paymentMethodRepository.save(oldPaymentMethodOptional);
        return paymentMethodMapper.toDto(oldPaymentMethodOptional);
    }

    @Override
    public void deleteById(UUID id) {
        paymentMethodRepository.deleteById(id);
    }
}
