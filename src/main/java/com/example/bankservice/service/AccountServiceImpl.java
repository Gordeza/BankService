package com.example.bankservice.service;

import com.example.bankservice.model.Account;
import com.example.bankservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    @Override
    public Account save(Account a) {
        return accountRepository.save(a);
    }
}
