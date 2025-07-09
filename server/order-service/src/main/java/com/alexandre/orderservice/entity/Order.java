package com.alexandre.orderservice.entity;

import com.alexandre.orderservice.enums.OrderState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private UUID profileId;

    @NotNull
    private UUID addressId;

    @NotNull
    private OrderState state;

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Coupon> appliedCoupons;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
