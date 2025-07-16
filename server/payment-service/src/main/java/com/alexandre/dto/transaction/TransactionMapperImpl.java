package com.alexandre.dto.transaction;

import com.alexandre.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapperImpl implements TransactionMapper {
    @Override
    public Transaction toEntity(TransactionDTO transactionDTO) {
        return Transaction.builder()
                .createdAt(transactionDTO.getCreatedAt())
                .id(transactionDTO.getId())
                .state(transactionDTO.getState())
                .amount(transactionDTO.getAmount())
                .orderId(transactionDTO.getOrderId())
                .stripeSessionId(transactionDTO.getStripeSessionId())
                .build();
    }

    @Override
    public TransactionDTO toDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .createdAt(transaction.getCreatedAt())
                .id(transaction.getId())
                .state(transaction.getState())
                .amount(transaction.getAmount())
                .orderId(transaction.getOrderId())
                .stripeSessionId(transaction.getStripeSessionId())
                .build();
    }
}
