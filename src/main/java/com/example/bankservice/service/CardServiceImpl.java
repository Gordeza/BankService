package com.example.bankservice.service;

import com.example.bankservice.model.Account;
import com.example.bankservice.model.Card;
import com.example.bankservice.model.Transaction;
import com.example.bankservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{

    @Autowired
    private final CardRepository cardRepository;

    @Autowired
    private final AccountService accountService;

    @Autowired
    private final TransactionService transactionService;

    @Override
    public Boolean validateCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if(card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card doesn't belong to the bank");
        }
        if(!card.getIsNotLocked()) {
            throw new ResponseStatusException(HttpStatus.LOCKED, "Card is locked");
        }
        return true;
    }

    @Override
    public Card findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }

    @Override
    @Transactional
    public Transaction deposit(Card card, Double amount) {
        Account account = card.getAccount();
        account.setBalance(account.getBalance() + amount);
        accountService.save(account);
        Transaction transaction =
                Transaction.builder()
                        .account(account)
                        .amount(amount)
                        .date(new Date())
                        .transactionType("Deposit")
                        .build();
        transactionService.save(transaction);
        return transaction;
    }

    @Override
    @Transactional
    public Transaction withdraw(Card card, Double amount) {
        Account account = card.getAccount();
        if(account.getBalance() < amount) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Account balance exceeded");
        account.setBalance(account.getBalance() - amount);
        accountService.save(account);
        Transaction transaction =
                Transaction.builder()
                        .account(account)
                        .amount(amount)
                        .date(new Date())
                        .transactionType("Withdraw")
                        .build();
        transactionService.save(transaction);
        return transaction;
    }

    @Override
    public Transaction balance(Card card) {
        Account account = card.getAccount();
        Transaction transaction =
                Transaction.builder()
                        .account(account)
                        .amount(account.getBalance())
                        .date(new Date())
                        .transactionType("Balance")
                        .build();
        transactionService.save(transaction);
        return transaction;
    }
}
