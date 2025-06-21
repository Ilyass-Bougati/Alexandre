package com.alexandre.inventoryservice.enums;

public enum UnitState {
    AVAILABLE,     // Unit is in stock and ready for reservation or sale
    RESERVED,      // Temporarily held for an order
    SOLD,          // Successfully sold and no longer in inventory
    DAMAGED,       // Physically damaged or unusable
    RETURNED,      // Returned by customer (could be AVAILABLE again after inspection)
    LOST           // Lost or missing from warehouse
}
