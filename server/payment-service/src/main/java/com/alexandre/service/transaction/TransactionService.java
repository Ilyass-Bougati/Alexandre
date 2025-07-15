package com.alexandre.service.transaction;

import com.alexandre.dto.transaction.TransactionDTO;

public interface TransactionService {
    TransactionDTO save(TransactionDTO transactionDTO);
}
