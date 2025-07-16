package com.alexandre.service.payment;

import com.alexandre.dto.response.OrderDTO;
import com.alexandre.dto.transaction.TransactionDTO;
import com.alexandre.enums.TransactionState;
import com.alexandre.event.PaymentSuccessEvent;
import com.alexandre.service.order.OrderService;
import com.alexandre.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TransactionService transactionService;
    private final KafkaTemplate<String, PaymentSuccessEvent> kafkaTemplate;
    private final OrderService orderService;

    @Override
    public void successPayment(UUID transactionId, Jwt jwt) {
        transactionService.updateState(transactionId, TransactionState.SUCCESSFUL);
        TransactionDTO transactionDTO = transactionService.findById(transactionId);
        OrderDTO orderDTO = orderService.get(transactionDTO.getOrderId(), jwt);

        // creating an event and producing an event
        PaymentSuccessEvent paymentSuccessEvent = PaymentSuccessEvent.builder()
                .transactionId(transactionId)
                .profileId(orderDTO.getProfileId())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        kafkaTemplate.send("payment.success", paymentSuccessEvent);
        log.info("Successfully handled payment for transactionId={}, orderId={}, profileId={}", transactionId, transactionDTO.getOrderId(), orderDTO.getProfileId());
    }

    @Override
    public void cancelPayment(UUID transactionId, Jwt jwt) {
        transactionService.updateState(transactionId, TransactionState.FAILED);
        // TODO : idk what to do here now, check this later
    }
}
