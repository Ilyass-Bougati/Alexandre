package com.alexandre.dto.transaction;

import com.alexandre.entity.Transaction;

public interface TransactionMapper {
    Transaction toEntity(TransactionDTO transactionDTO);
    TransactionDTO toDTO(Transaction transaction);
}
