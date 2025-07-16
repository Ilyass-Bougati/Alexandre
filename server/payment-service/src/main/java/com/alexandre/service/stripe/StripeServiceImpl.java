package com.alexandre.service.stripe;

import com.alexandre.dto.response.OrderDTO;
import com.alexandre.dto.response.OrderItemDTO;
import com.alexandre.dto.StripeResponse;
import com.alexandre.dto.transaction.TransactionDTO;
import com.alexandre.enums.OrderState;
import com.alexandre.exception.PaymentException;
import com.alexandre.record.StripeProperties;
import com.alexandre.service.order.OrderService;
import com.alexandre.service.transaction.TransactionService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class StripeServiceImpl implements StripeService {

    private final StripeProperties stripeProperties;
    private final OrderService orderService;
    private final TransactionService transactionService;

    public StripeServiceImpl(StripeProperties stripeProperties, OrderService orderService, TransactionService transactionService) {
        this.transactionService = transactionService;
        this.stripeProperties = stripeProperties;
        this.orderService = orderService;
    }

    // TODO : changes
    // The currency will have to be rethinked later
    public StripeResponse checkout(UUID orderId, Jwt jwt) {
        // getting the order data
        OrderDTO orderDTO = orderService.get(orderId, jwt);
        log.info("Checkout order: {}", orderDTO);
        log.info("secret : {}", stripeProperties.secretKey());

        // checking that the order is still pending
        if (orderDTO.getState() != OrderState.PENDING) {
            throw new PaymentException("Order state is not PENDING");
        }

        List<SessionCreateParams.LineItem> items = new ArrayList<>();

        // creating a transaction
        TransactionDTO transaction = TransactionDTO.builder()
                .orderId(orderId)
                .amount(0L)
                .build();

        for (OrderItemDTO orderItem : orderDTO.getItems()) {
            // creating the stripe session
            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(orderItem.getProductName()).build();
            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("USD")
                    .setUnitAmount(orderItem.getUnitPriceAtOrderTime().longValue())
                    .setProductData(productData)
                    .build();
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(orderItem.getQuantity().longValue())
                    .setPriceData(priceData)
                    .build();
            items.add(lineItem);

            // adding the price to the transaction price
            transaction.setAmount(transaction.getAmount() + orderItem.getUnitPriceAtOrderTime().longValue());
        }

        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(stripeProperties.successUrl())
                .setCancelUrl(stripeProperties.cancelUrl())
                .addAllLineItem(items)
                .build();

        Session session;

        try {
            session = Session.create(sessionCreateParams);
        } catch (StripeException e) {
            throw new PaymentException("Error generating payment session, try again later");
        }

        // if the session created successfully, we create a transaction
        transaction.setStripeSessionId(session.getId());
        transactionService.save(transaction);

        return StripeResponse.builder()
                .message("Stripe link generated successfully")
                .status("SUCCESS")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
