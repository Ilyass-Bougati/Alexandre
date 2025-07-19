package com.alexandre.service.payment;

import com.alexandre.dto.response.OrderDTO;
import com.alexandre.dto.response.ProfileDTO;
import com.alexandre.dto.transaction.TransactionDTO;
import com.alexandre.enums.TransactionState;
import com.alexandre.event.PaymentEvent;
import com.alexandre.service.order.OrderService;
import com.alexandre.service.profile.ProfileService;
import com.alexandre.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TransactionService transactionService;
    private final KafkaTemplate<String, PaymentEvent> kafkaPaymentTemplate;
    private final OrderService orderService;
    private final ProfileService profileService;

    @Override
    public void successPayment(UUID transactionId, Jwt jwt) {
        transactionService.updateState(transactionId, TransactionState.SUCCESSFUL);
        TransactionDTO transactionDTO = transactionService.findById(transactionId);
        OrderDTO orderDTO = orderService.get(transactionDTO.getOrderId(), jwt);
        ProfileDTO profileDTO = profileService.get(jwt);

        // creating an event and producing an event
        PaymentEvent paymentSuccessEvent = PaymentEvent.builder()
                .transactionId(transactionId)
                .profileId(orderDTO.getProfileId())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .email(profileDTO.getEmail())
                .build();

        kafkaPaymentTemplate.send("payment.success", paymentSuccessEvent);
        log.info("Successfully handled payment for transactionId={}, orderId={}, profileId={}", transactionId, transactionDTO.getOrderId(), orderDTO.getProfileId());
    }

    @Override
    public void cancelPayment(UUID transactionId, Jwt jwt) {
        transactionService.updateState(transactionId, TransactionState.FAILED);
        TransactionDTO transactionDTO = transactionService.findById(transactionId);
        OrderDTO orderDTO = orderService.get(transactionDTO.getOrderId(), jwt);

        // producing a cancel event
        PaymentEvent cancelEvent = PaymentEvent.builder()
                .transactionId(transactionId)
                .profileId(orderDTO.getProfileId())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        kafkaPaymentTemplate.send("payment.cancel", cancelEvent);
        log.info("Successfully handled canceling payment for transactionId={}, orderId={}, profileId={}", transactionId, transactionDTO.getOrderId(), orderDTO.getProfileId());
    }

    // TODO : add the refunding logic
    @Override
    public void refundPayment(UUID transactionId, Jwt jwt) {
        transactionService.updateState(transactionId, TransactionState.REFUNDED);
        TransactionDTO transactionDTO = transactionService.findById(transactionId);
        OrderDTO orderDTO = orderService.get(transactionDTO.getOrderId(), jwt);
        ProfileDTO profileDTO = profileService.get(jwt);

        // producing a refunded event
        PaymentEvent refundEvent = PaymentEvent.builder()
                .transactionId(transactionId)
                .profileId(orderDTO.getProfileId())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .email(profileDTO.getEmail())
                .build();

        kafkaPaymentTemplate.send("payment.refund", refundEvent);
        log.info("Successfully handled refunding payment for transactionId={}, orderId={}, profileId={}", transactionId, transactionDTO.getOrderId(), orderDTO.getProfileId());
    }


}
