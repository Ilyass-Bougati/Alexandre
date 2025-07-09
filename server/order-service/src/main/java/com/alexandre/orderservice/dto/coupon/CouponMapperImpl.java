package com.alexandre.orderservice.dto.coupon;

import com.alexandre.orderservice.entity.Coupon;
import org.springframework.stereotype.Service;

@Service
public class CouponMapperImpl implements CouponMapper {
    @Override
    public Coupon toEntity(CouponDTO couponDTO) {
        return Coupon.builder()
                .code(couponDTO.getCode())
                .id(couponDTO.getId())
                .discount(couponDTO.getDiscount())
                .build();
    }

    @Override
    public CouponDTO toDto(Coupon coupon) {
        return CouponDTO.builder()
                .code(coupon.getCode())
                .id(coupon.getId())
                .discount(coupon.getDiscount())
                .build();
    }
}
