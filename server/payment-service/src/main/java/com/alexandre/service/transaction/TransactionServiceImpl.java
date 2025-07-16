package com.alexandre.service.transaction;

import com.alexandre.dto.transaction.TransactionDTO;
import com.alexandre.dto.transaction.TransactionMapper;
import com.alexandre.entity.Transaction;
import com.alexandre.enums.TransactionState;
import com.alexandre.exception.NotFoundException;
import com.alexandre.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Override
    @Transactional(readOnly = true)
    public TransactionDTO findById(UUID id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDTO> findAll() {
        return transactionRepository.findAll()
                .stream().map(transactionMapper::toDTO).toList();
    }

    @Override
    public TransactionDTO updateState(UUID id, TransactionState state) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
        transaction.setState(state);
        return transactionMapper.toDTO(transactionRepository.save(transaction));
    }
}
