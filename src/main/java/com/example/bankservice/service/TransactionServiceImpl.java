package com.example.bankservice.service;

import com.example.bankservice.model.Transaction;
import com.example.bankservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;


    @Override
    public Transaction save(Transaction t) {
        return transactionRepository.save(t);
    }
}
