package com.alexandre.service.transaction;

import com.alexandre.dto.transaction.TransactionDTO;
import com.alexandre.enums.TransactionState;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    TransactionDTO save(TransactionDTO transactionDTO);
    TransactionDTO findById(UUID id);
    List<TransactionDTO> findAll();
    TransactionDTO updateState(UUID id, TransactionState state);
    Boolean checkProfileOwnsTransaction(UUID profileId, UUID transactionId, Jwt jwt);
    boolean existsTransaction(UUID orderId);
}
