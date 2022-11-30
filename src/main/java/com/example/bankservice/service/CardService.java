package com.example.bankservice.service;

import com.example.bankservice.model.Card;
import com.example.bankservice.model.Transaction;

public interface CardService {

    Boolean validateCard(String cardNumber);

    Card findByCardNumber(String cardNumber);

    Transaction deposit(Card card, Double amount);

    Transaction withdraw(Card card, Double amount);

    Transaction balance(Card card);
}
