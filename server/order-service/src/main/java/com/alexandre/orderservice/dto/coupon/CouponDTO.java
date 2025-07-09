package com.alexandre.orderservice.dto.coupon;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
    private UUID id;
    private String code;
    private Double discount;
}
