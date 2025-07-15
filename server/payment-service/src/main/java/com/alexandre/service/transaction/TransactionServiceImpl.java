package com.alexandre.service.transaction;

import com.alexandre.dto.transaction.TransactionDTO;
import com.alexandre.dto.transaction.TransactionMapper;
import com.alexandre.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionDTO save(TransactionDTO transactionDTO) {
        return transactionMapper.toDTO(transactionRepository.save(transactionMapper.toEntity(transactionDTO)));
    }
}
