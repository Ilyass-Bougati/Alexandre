package com.alexandre.repository;

import com.alexandre.entity.Transaction;
import com.alexandre.enums.TransactionState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Transaction findByOrderId(UUID orderId);
}
