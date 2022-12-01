package com.example.bankservice.service;

import com.example.bankservice.exception.CardLockedException;
import com.example.bankservice.exception.InvalidCardException;
import com.example.bankservice.model.Card;
import com.example.bankservice.model.Transaction;

public interface CardService {

    Boolean validateCard(String cardNumber) throws InvalidCardException, CardLockedException;

    Card getCard(String cardNumber);

    Card save(Card card);

    Transaction deposit(Card card, Double amount);

    Transaction withdraw(Card card, Double amount);

    Double balance(Card card);

    Card updatePin(Card card, String pin);
}
