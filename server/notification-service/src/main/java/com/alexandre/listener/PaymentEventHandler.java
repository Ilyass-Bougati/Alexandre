package com.alexandre.listener;

import com.alexandre.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentEventHandler {

    @KafkaListener(topics = "payment.success")
    public void processPaymentSuccessEvent(PaymentEvent paymentEvent) {
        log.info("Processing payment success event {}", paymentEvent.getTransactionId());
    }

    @KafkaListener(topics = "payment.cancel")
    public void processPaymentCancelEvent(PaymentEvent paymentEvent) {
        log.info("Processing payment cancel event {}", paymentEvent.getTransactionId());
    }

    @KafkaListener(topics = "payment.refund")
    public void processPaymentRefundEvent(PaymentEvent paymentEvent) {
        log.info("Processing payment refund event {}", paymentEvent.getTransactionId());
    }
}
