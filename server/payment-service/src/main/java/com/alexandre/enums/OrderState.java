package com.alexandre.enums;

public enum OrderState {
    PENDING,           // Order created but not yet confirmed from the user side, which mean it can still be modified
    CONFIRMED,         // Order validated (e.g., payment confirmed or stock checked)
    PREPARING,         // Being prepared (e.g., packaging, picking from warehouse)
    SHIPPED,           // Shipped from warehouse, in transit
    DELIVERED,         // Successfully delivered to user
    CANCELED,          // Canceled by user or system
    RETURNED,          // User returned order
    FAILED             // Payment or processing failed
}