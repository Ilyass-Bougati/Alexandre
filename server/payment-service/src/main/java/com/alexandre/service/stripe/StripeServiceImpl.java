package com.alexandre.service.stripe;

import com.alexandre.dto.response.OrderDTO;
import com.alexandre.dto.response.OrderItemDTO;
import com.alexandre.dto.StripeResponse;
import com.alexandre.enums.OrderState;
import com.alexandre.exception.PaymentException;
import com.alexandre.record.StripeProperties;
import com.alexandre.service.order.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StripeServiceImpl implements StripeService {

    private final WebClient webClient;
    private final StripeProperties stripeProperties;
    private final OrderService orderService;

    public StripeServiceImpl(WebClient.Builder clientBuilder, StripeProperties stripeProperties, OrderService orderService) {
        this.webClient = clientBuilder.build();
        this.stripeProperties = stripeProperties;
        this.orderService = orderService;
    }

    // TODO : changes
    // The currency will have to be rethinked later
    public StripeResponse checkout(UUID orderId, Jwt jwt) {
        // getting the order data
        OrderDTO orderDTO = orderService.get(orderId, jwt);

        // checking that the order is still pending
        if (orderDTO.getState() != OrderState.PENDING) {
            throw new PaymentException("Order state is not PENDING");
        }

        List<SessionCreateParams.LineItem> items = new ArrayList<>();

        for (OrderItemDTO orderItem : orderDTO.getItems()) {
            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(orderItem.getProductName()).build();
            // price
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

        return StripeResponse.builder()
                .message("Stripe link generated successfully")
                .status("SUCCESS")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();

    }
}
