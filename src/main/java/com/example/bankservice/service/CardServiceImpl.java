package com.example.bankservice.service;

import com.example.bankservice.DAO.TransactionStatus;
import com.example.bankservice.exception.AmountExceededException;
import com.example.bankservice.exception.CardLockedException;
import com.example.bankservice.exception.InvalidCardException;
import com.example.bankservice.model.Account;
import com.example.bankservice.model.Card;
import com.example.bankservice.model.Transaction;
import com.example.bankservice.DAO.TransactionType;
import com.example.bankservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{

    private final CardRepository cardRepository;

    private final AccountService accountService;

    private final TransactionService transactionService;

    private final PasswordEncoder encoder;

    @Override
    public Boolean validateCard(String cardNumber) throws InvalidCardException, CardLockedException {
        Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(() -> new InvalidCardException("Card wasn't validated"));
        if(!card.getIsNotLocked()) {
            throw new CardLockedException("Card is locked");
        }
        return true;
    }

    @Override
    public Card getCard(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber).orElseThrow(() -> new InvalidCardException("Card doesn't belong to bank"));
    }

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
//    @Transactional
    public Transaction deposit(Card card, Double amount) {
        Account account = card.getAccount();
        account.setBalance(account.getBalance() + amount);
        accountService.save(account);
        Transaction transaction =
                Transaction.builder()
                        .account(account)
                        .amount(amount)
                        .date(new Date())
                        .transactionType(TransactionType.DEPOSIT)
                        .status(TransactionStatus.SUCCESSFUL)
                        .build();
        transactionService.save(transaction);
        return transaction;
    }

    @Override
//    @Transactional
    public Transaction withdraw(Card card, Double amount) {
        Account account = card.getAccount();
        if(account.getBalance() < amount) {
            Transaction transactionFailed =
                    Transaction.builder()
                            .account(account)
                            .amount(amount)
                            .date(new Date())
                            .transactionType(TransactionType.WITHDRAW)
                            .status(TransactionStatus.FAILED)
                            .build();
            transactionService.save(transactionFailed);
            throw new AmountExceededException("Not enough amount on balance");
        }
        account.setBalance(account.getBalance() - amount);
        accountService.save(account);
        Transaction transaction =
                Transaction.builder()
                        .account(account)
                        .amount(amount)
                        .date(new Date())
                        .transactionType(TransactionType.WITHDRAW)
                        .status(TransactionStatus.SUCCESSFUL)
                        .build();
        transactionService.save(transaction);
        return transaction;
    }

    @Override
    public Double balance(Card card) {
        Account account = card.getAccount();
        Transaction transaction =
                Transaction.builder()
                        .account(account)
                        .amount(account.getBalance())
                        .date(new Date())
                        .transactionType(TransactionType.BALANCE)
                        .status(TransactionStatus.SUCCESSFUL)
                        .build();
        transactionService.save(transaction);
        return card.getAccount().getBalance();
    }

    @Override
    public Card updatePin(Card card, String pin) {
        card.setPin(encoder.encode(pin));
        return cardRepository.save(card);
    }
}
