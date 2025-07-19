package com.alexandre.listener;

import com.alexandre.event.PaymentEvent;
import com.alexandre.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventHandler {

    private final EmailService emailService;

    @KafkaListener(topics = "payment.success")
    public void processPaymentSuccessEvent(PaymentEvent paymentEvent) {
        emailService.send(paymentEvent.getEmail(), "Successful payment", "payment-success.html");
        log.info("Processing payment success event {}", paymentEvent.getTransactionId());
    }

    @KafkaListener(topics = "payment.cancel")
    public void processPaymentCancelEvent(PaymentEvent paymentEvent) {
        emailService.send(paymentEvent.getEmail(), "Cancelled payment", "payment-cancel.html");
        log.info("Processing payment cancel event {}", paymentEvent.getTransactionId());
    }

    @KafkaListener(topics = "payment.refund")
    public void processPaymentRefundEvent(PaymentEvent paymentEvent) {
        emailService.send(paymentEvent.getEmail(), "Refunded payment", "payment-refund.html");
        log.info("Processing payment refund event {}", paymentEvent.getTransactionId());
    }
}
